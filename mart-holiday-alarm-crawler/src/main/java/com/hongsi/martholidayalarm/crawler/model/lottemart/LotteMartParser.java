package com.hongsi.martholidayalarm.crawler.model.lottemart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.location.Location;
import com.hongsi.martholidayalarm.core.mart.MartType;
import com.hongsi.martholidayalarm.crawler.MartCrawlerType;
import com.hongsi.martholidayalarm.crawler.model.MartParser;
import com.hongsi.martholidayalarm.crawler.model.holiday.MonthDayHoliday;
import com.hongsi.martholidayalarm.crawler.utils.MatchSpliterator;
import com.hongsi.martholidayalarm.crawler.utils.RegionParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LotteMartParser implements MartParser {

    private static final DateTimeFormatter HOLIDAY_FORMATTER = DateTimeFormatter.ofPattern("M/d");
    private static final List<Pattern> HOLIDAY_PATTERNS = asList(Pattern.compile("\\d+/\\d+"), Pattern.compile("\\d+월\\s?\\d+일"));
    private static final Pattern PATTERN_HOLIDAY_BY_ONE = Pattern.compile("(.+\\s*[째|쨰]\\s*.요일)(, *.+\\s*[째|쨰]\\s*.요일)+");
    private static final Pattern PATTERN_EXCLUDE_HOLIDAY = Pattern.compile("(\\d+\\s*월?\\s*/?\\s*\\d+\\s*일?)\\s*(\\(?(.요일|月|火|水|木|金|土|日|월|화|수|목|금|토|일)\\)?)?\\s*정상\\s*영업");

    @JsonProperty("brnchCd")
    private String realId;

    @JsonProperty("strNm")
    private String branchName;

    @JsonProperty("newAddress")
    private String roadNameAddress;

    @JsonProperty("oldAddress")
    private String oldAddress;

    @JsonProperty("repTelNo")
    private String phoneNumber;

    @JsonProperty("openTime")
    private String openTime;

    @JsonProperty("closedTime")
    private String closeTime;

    @JsonProperty("xcoord")
    private String rawLatitude;

    @JsonProperty("ycoord")
    private String rawLongitude;

    @JsonIgnore
    private Location location;

    private String holidayText;
    private List<Holiday> holidays;

    @JsonProperty("holiDate")
    public void setHoliday(String holidayText) {
        this.holidayText = holidayText;
        this.holidays = getHolidays();
    }

    @Override
    public MartType getMartType() {
        return MartType.LOTTEMART;
    }

    @Override
    public String getRealId() {
        return realId;
    }

    @Override
    public String getBranchName() {
        return branchName;
    }

    @Override
    public String getRegion() {
        return RegionParser.getRegionFromAddress(getAddress());
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getAddress() {
        return StringUtils.hasText(roadNameAddress) ? roadNameAddress : oldAddress;
    }

    @Override
    public String getOpeningHours() {
        return String.format("%s ~ %s", openTime, closeTime);
    }

    @Override
    public String getUrl() {
        String suffix = "/branch/bc/main.do?brnchCd=" + getRealId();
        return MartCrawlerType.LOTTEMART.appendUrl(suffix);
    }

    @Override
    public Location getLocation() {
        if (Objects.isNull(location)) {
            location = Location.parse(rawLatitude, rawLongitude);
        }
        return location;
    }

    @Override
    public String getHolidayText() {
        return holidayText;
    }

    @Override
    public List<Holiday> getHolidays() {
        List<Holiday> holidays = HOLIDAY_PATTERNS.stream()
                .map(pattern -> pattern.matcher(holidayText))
                .map(this::parseHoliday)
                .reduce(Stream::concat)
                .orElseGet(Stream::empty)
                .filter(Holiday::isUpcoming)
                .collect(Collectors.toList());

        // 정기 휴무일로 날짜 생성
        if (holidays.isEmpty()) {
            Matcher holidayMatcher = PATTERN_HOLIDAY_BY_ONE.matcher(getHolidayText());
            if (holidayMatcher.find()) {
                for (int i = 1; i <= holidayMatcher.groupCount(); i++) {
                    holidays.addAll(generateRegularHolidays(holidayMatcher.group(i)));
                }
            } else {
                holidays.addAll(generateRegularHolidays(getHolidayText()));
            }
        }

        return excludeHoliday(holidays);
    }

    private Stream<Holiday> parseHoliday(Matcher matcher) {
        Stream<Holiday> holidayStream = Stream.empty();
        while (matcher.find()) {
            // 포매터가 파싱할 수 있게 문자열 변경
            String holidayText = changeToDateFormat(matcher.group());
            MonthDayHoliday monthDayHoliday = MonthDayHoliday.parse(holidayText, HOLIDAY_FORMATTER);
            Stream<Holiday> parsedHolidayStream = Stream.of(monthDayHoliday.toHoliday());
            holidayStream = Stream.concat(holidayStream, parsedHolidayStream);
        }
        return holidayStream;
    }

    // 정상 영업 정보도 휴무일 텍스트에 같이 포함되기 때문에 확인해서 제거
    public List<Holiday> excludeHoliday(List<Holiday> holidays) {
        String openHolidayText = MatchSpliterator.from(PATTERN_EXCLUDE_HOLIDAY, holidayText, 1)
                .stream()
                .findFirst()
                .map(this::changeToDateFormat)
                .orElse("");
        if (openHolidayText.isEmpty()) {
            return holidays;
        }

        try {
            Holiday openHoliday = MonthDayHoliday.parse(openHolidayText, HOLIDAY_FORMATTER).toHoliday();
            return holidays.stream()
                    .filter(holiday -> !holiday.equals(openHoliday))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("failed to parse holiday", e);
            return holidays;
        }
    }

    private String changeToDateFormat(String openHolidyText) {
        return openHolidyText
                .replaceAll("\\s*월\\s*", "\\/")
                .replaceAll("\\s*일\\s*", "");
    }
}
