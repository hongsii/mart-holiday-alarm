package com.hongsi.martholidayalarm.domain.crawler.mart;

import static java.util.Arrays.asList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartType;
import com.hongsi.martholidayalarm.domain.crawler.holiday.MonthDayHoliday;
import com.hongsi.martholidayalarm.domain.crawler.holiday.SolarLunarConverter;
import com.hongsi.martholidayalarm.domain.mart.Crawlable;
import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.utils.MatchSpliterator;
import com.hongsi.martholidayalarm.utils.RegionParser;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.Jsoup;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CostcoData implements Crawlable {

	private static final Pattern PATTERN_OPENING_HOURS = Pattern.compile("(오전|오후)\\s{0,}\\d{1,2}:\\d{1,2}");
	private static final Pattern PATTERN_HOLIDAY_TEXT = Pattern.compile("(매월.+?)(?=의무 휴무합니다.)");
	private static final Pattern PATTERN_EXCLUDE_HOLIDAY_TEXT = Pattern.compile("(?=\\d+월)(.+?)(?=\\s+?\\(.요일\\)\\s+?정상영업\\s+?합니다.)");
	private static final DateTimeFormatter HOLIDAY_FORMATTER = DateTimeFormatter.ofPattern("M d");
	private static final Pattern PATTERN_HOLIDAY_BY_ONE = Pattern.compile("(.+\\s{0,}[째|쨰]\\s{0,}.요일)(, {0,}.+\\s{0,}[째|쨰]\\s{0,}.요일)+");

	@JsonProperty("name")
	private String realId;

	@JsonProperty("displayName")
	private String displayName;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("line1")
	private String line1;

	@JsonProperty("latitude")
	private String rawLatitude;

	@JsonProperty("longitude")
	private String rawLongitude;

	@JsonProperty("storeContent")
	private String storeContent;

	@JsonIgnore
	private Location location;

	@Override
	public MartType getMartType() {
		return MartType.COSTCO;
	}

	@Override
	public String getRealId() {
		return realId;
	}

	@Override
	public String getBranchName() {
		return displayName;
	}

	@Override
	public String getRegion() {
		return RegionParser.getRegionFromAddress(getAddress());
	}

	@Override
	public String getPhoneNumber() {
		return phone;
	}

	@Override
	public String getAddress() {
		return line1;
	}

	@Override
	public String getOpeningHours() {
		return MatchSpliterator.from(PATTERN_OPENING_HOURS, storeContent).stream()
				.collect(Collectors.joining(" - "));
	}

	@Override
	public String getUrl() {
		return CrawlerMartType.COSTCO.appendUrl("/store-finder");
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
		MatchSpliterator matcher = MatchSpliterator.from(PATTERN_HOLIDAY_TEXT, getUnwrappedStoreContent());
		return matcher.stream()
				.findFirst()
				.map(String::trim)
				.orElse("");
	}

	@Override
	public List<Holiday> getHolidays() {
		List<Holiday> holidays = getFixedHolidays(LocalDate.now());
		// 둘째 수요일, 넷째 일요일 처리
		Matcher holidayMatcher = PATTERN_HOLIDAY_BY_ONE.matcher(getHolidayText());
		if (holidayMatcher.find()) {
			for (int i = 1; i <= holidayMatcher.groupCount(); i++) {
				holidays.addAll(generateRegularHoliday(holidayMatcher.group(i)));
			}
		} else {
			holidays.addAll(generateRegularHoliday(getHolidayText()));
		}
		return excludeSpecifiedHoliday(holidays);
	}

	// 코스트코는 1/1, 설날, 추석이 휴무일
	public List<Holiday> getFixedHolidays(LocalDate current) {
		List<LocalDate> fixedDates = new ArrayList<>();
		fixedDates.addAll(getFixedDates(current.getYear()));
		fixedDates.addAll(getFixedDates(current.getYear() + 1));
		return fixedDates.stream()
				.filter(fixedDate -> !fixedDate.isBefore(current)
						&& (YearMonth.from(fixedDate).equals(YearMonth.from(current))
							|| YearMonth.from(fixedDate).equals(YearMonth.from(current.plusMonths(1))))
				)
				.map(Holiday::of)
				.collect(Collectors.toList());
	}

	private List<LocalDate> getFixedDates(int year) {
		LocalDate newYearOfLunar = LocalDate.of(year, 1, 1);
		LocalDate chuSeokOfLunar = LocalDate.of(year, 8, 15);
		return asList(
				newYearOfLunar,
				SolarLunarConverter.convertLunarToSolar(newYearOfLunar),
				SolarLunarConverter.convertLunarToSolar(chuSeokOfLunar)
		);
	}

	private List<Holiday> excludeSpecifiedHoliday(List<Holiday> holidays) {
		// 휴일에도 일부 영업하는 매장이 존재함
		MatchSpliterator matcher = MatchSpliterator.from(PATTERN_EXCLUDE_HOLIDAY_TEXT, getUnwrappedStoreContent(), 1);
		List<Holiday> excludingHolidays = matcher.stream()
				.map(holidayText -> holidayText.replaceAll("월|일", "").trim())
				.map(replacedText -> MonthDayHoliday.of(replacedText, HOLIDAY_FORMATTER).toHoliday())
				.collect(Collectors.toList());

		return holidays.stream()
				.filter(holiday -> !excludingHolidays.contains(holiday))
				.collect(Collectors.toList());
	}

	private String getUnwrappedStoreContent() {
		return Jsoup.parse(storeContent).text();
	}
}
