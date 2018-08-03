package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.crawler.util.CrawlUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LottemartPage extends MartPage {

	public static final String BASE_URL = "http://company.lottemart.com";

	LottemartPage(String url) throws IOException {
		super(url);
	}

	@Override
	public Mart getInfo() {
		return Mart.builder()
				.martType(MartType.LOTTEMART)
				.realId(getRealId())
				.region(getRegion())
				.branchName(getBranchName())
				.phoneNumber(getPhoneNumber())
				.address(getAddress())
				.url(getPageUrl())
				.holidays(getHolidays())
				.build();
	}

	private String getRealId() {
		final String removingUrlPattern = "^.*=";
		return getHomeUrl().replaceAll(removingUrlPattern, "");
	}

	private String getRegion() {
		return CrawlUtil.getRegionFromAddress(getAddress());
	}

	private String getBranchName() {
		return page.select("div#header > div.select > a").text();
	}

	private String getPhoneNumber() {
		return page.select("li.phone").text();
	}

	private String getAddress() {
		String addressTag = page.select("ul.info > li.add").text();
		Pattern pattern = Pattern.compile("(?:\\[[새|구]주소\\])[가-힣a-zA-Z\\d\\s]+");
		Matcher matcher = pattern.matcher(addressTag);
		String address = "";
		while (matcher.find() && address.isEmpty()) {
			address = matcher.group().replaceAll("\\[[새|구]주소\\]", "").trim();
		}
		return address;
	}

	private String getPageUrl() {
		return BASE_URL + getHomeUrl();
	}

	private String getHomeUrl() {
		final String HOME_URL = "ul#lnb > li.lnb1 > a";
		return page.select(HOME_URL).attr("href");
	}

	private List<Holiday> getHolidays() {
		String holidayTexts = page.select("div.visual > dl > dd").text();
		List<Holiday> holidays = getAnnouncedHolidays(holidayTexts);
		if (holidays.isEmpty()) {
			holidays.addAll(getRegularHolidaysByWeek(holidayTexts));
		}
		return holidays;
	}

	private List<Holiday> getAnnouncedHolidays(String holidayTexts) {
		List<Holiday> holidays = new ArrayList<>();
		final String[] patterns = {"\\d+\\/\\d+", "\\d+월\\s?\\d+일"};
		for (String pattern : patterns) {
			Matcher matcher = Pattern.compile(pattern).matcher(holidayTexts);
			while (matcher.find()) {
				holidays.add(Holiday.builder()
						.date(getParsedDate(matcher.group()
								.replaceAll("월\\s?", "\\/")
								.replaceAll("일", "")))
						.build());
			}
		}
		return holidays;
	}

	private LocalDate getParsedDate(String dateText) {
		MonthDay monthDay = MonthDay
				.parse(dateText, DateTimeFormatter.ofPattern("M/d"));
		return LocalDate.of(getYearFromMonth(monthDay.getMonth()),
				monthDay.getMonth(),
				monthDay.getDayOfMonth());
	}

	private int getYearFromMonth(Month parsedMonth) {
		YearMonth currentYearMonth = YearMonth.now();
		return (currentYearMonth.getMonth().getValue() >= Month.OCTOBER.getValue()
				&& parsedMonth.getValue() >= Month.JANUARY.getValue()) ?
				currentYearMonth.getYear() + 1 : currentYearMonth.getYear();
	}

	private List<Holiday> getRegularHolidaysByWeek(String holidayTexts) {
		RegularHoliday regularHoliday = new RegularHoliday(holidayTexts);
		return regularHoliday.makeRegularHolidays();
	}
}
