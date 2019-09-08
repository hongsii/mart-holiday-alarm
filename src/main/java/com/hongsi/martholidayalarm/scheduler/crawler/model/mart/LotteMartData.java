package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.MonthDayHoliday;
import com.hongsi.martholidayalarm.utils.MatchSpliterator;
import com.hongsi.martholidayalarm.utils.RegionParser;
import com.hongsi.martholidayalarm.utils.ValidationUtils;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LotteMartData implements Crawlable {

	private static final DateTimeFormatter HOLIDAY_FORMATTER = DateTimeFormatter.ofPattern("M/d");
	private static final List<Pattern> HOLIDAY_PATTERNS = asList(Pattern.compile("\\d+\\/\\d+"), Pattern.compile("\\d+월\\s?\\d+일"));
	private static final Pattern PATTERN_HOLIDAY_BY_ONE = Pattern.compile("(.+\\s{0,}[째|쨰]\\s{0,}.요일)(, {0,}.+\\s{0,}[째|쨰]\\s{0,}.요일)+");
	private static final Pattern PATTERN_EXCLUDE_HOLIDAY = Pattern.compile("(\\d+\\s{0,}월?\\s{0,}\\/?\\s{0,}\\d+\\s{0,}일?)\\s{0,}(\\(?(.요일|月|火|水|木|金|土|日|월|화|수|목|금|토|일)\\)?)?\\s{0,}정상\\s{0,}영업");

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

	@JsonProperty("holiDate")
	private String holidayText;

	@JsonProperty("xcoord")
	private String rawLatitude;

	@JsonProperty("ycoord")
	private String rawLongitude;

	@JsonIgnore
	private Location location;

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
		if (ValidationUtils.isNotBlank(roadNameAddress)) {
			return roadNameAddress;
		}
		return oldAddress;
	}

	@Override
	public String getOpeningHours() {
		return String.format("%s ~ %s", openTime, closeTime);
	}

	@Override
	public String getUrl() {
		String suffix = "/branch/bc/main.do?brnchCd=" + getRealId();
		return CrawlerMartType.LOTTEMART.appendUrl(suffix);
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
				.map(matcher -> parseHoliday(matcher))
				.reduce(Stream::concat)
				.orElseGet(Stream::empty)
				.filter(Holiday::isUpcoming)
				.collect(Collectors.toList());

		// 정기 휴무일로 날짜 생성
		if (holidays.isEmpty()) {
			Matcher holidayMatcher = PATTERN_HOLIDAY_BY_ONE.matcher(getHolidayText());
			if (holidayMatcher.find()) {
				for (int i = 1; i <= holidayMatcher.groupCount(); i++) {
					holidays.addAll(generateRegularHoliday(holidayMatcher.group(i)));
				}
			} else {
				holidays.addAll(generateRegularHoliday(getHolidayText()));
			}
		}

		return excludeHoliday(holidays);
	}

	private Stream<Holiday> parseHoliday(Matcher matcher) {
		Stream<Holiday> holidayStream = Stream.empty();
		while (matcher.find()) {
			// 포매터가 파싱할 수 있게 문자열 변경
			String holidayText = changeToDateFormat(matcher.group());
			MonthDayHoliday monthDayHoliday = MonthDayHoliday.of(holidayText, HOLIDAY_FORMATTER);
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

		if ("".equals(openHolidayText)) {
			return holidays;
		}

		try {
			Holiday openHoliday = MonthDayHoliday.of(openHolidayText, HOLIDAY_FORMATTER).toHoliday();
			return holidays.stream()
					.filter(holiday -> !holiday.equals(openHoliday))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return holidays;
		}
	}

	private String changeToDateFormat(String openHolidyText) {
		return openHolidyText
				.replaceAll("\\s{0,}월\\s{0,}", "\\/")
				.replaceAll("\\s{0,}일\\s{0,}", "");
	}
}
