package com.hongsi.martholidayalarm.crawler.domain.mart.lottemart;

import static java.util.Arrays.asList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.common.utils.ValidationUtils;
import com.hongsi.martholidayalarm.crawler.domain.CrawlerMartData;
import com.hongsi.martholidayalarm.crawler.domain.CrawlerMartType;
import com.hongsi.martholidayalarm.crawler.domain.holiday.MonthDayHoliday;
import com.hongsi.martholidayalarm.crawler.utils.RegionParser;
import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class LotteMartData extends CrawlerMartData {

	private static final DateTimeFormatter HOLIDAY_FORMATTER = DateTimeFormatter.ofPattern("M/d");
	private static final List<Pattern> HOLIDAY_PATTERNS = asList(
			Pattern.compile("\\d+\\/\\d+"), Pattern.compile("\\d+월\\s?\\d+일"));

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
	public List<Holiday> getHolidays() {
		List<Holiday> holidays = HOLIDAY_PATTERNS.stream()
				.map(pattern -> pattern.matcher(holidayText))
				.map(matcher -> parseHoliday(matcher))
				.reduce(Stream::concat)
				.orElseGet(Stream::empty)
				.filter(Holiday::isUpcoming)
				.collect(Collectors.toList());
		if (!holidays.isEmpty()) {
			return holidays;
		}

		return generateRegularHoliday(holidayText);
	}

	private Stream<Holiday> parseHoliday(Matcher matcher) {
		Stream<Holiday> holidayStream = Stream.empty();
		while (matcher.find()) {
			// 포매터가 파싱할 수 있게 문자열 변경
			String holidayText = matcher.group()
					.replaceAll("월\\s?", "\\/")
					.replaceAll("일", "");
			MonthDayHoliday monthDayHoliday = MonthDayHoliday.of(holidayText, HOLIDAY_FORMATTER);
			Stream<Holiday> parsedHolidayStream = Stream.of(monthDayHoliday.toHoliday());
			holidayStream = Stream.concat(holidayStream, parsedHolidayStream);
		}
		return holidayStream;
	}
}
