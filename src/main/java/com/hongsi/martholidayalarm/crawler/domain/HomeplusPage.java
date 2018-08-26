package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.crawler.util.CrawlUtil;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeplusPage extends MartPage {

	public static final String BASE_URL = "http://corporate.homeplus.co.kr";

	private MartType martype;

	HomeplusPage(MartType martType, String url) throws IOException {
		super(url);
		martype = martType;
	}

	@Override
	public Mart getInfo() {
		return Mart.builder()
				.martType(martype)
				.realId(getRealId())
				.region(getRegion())
				.branchName(getBranchName())
				.phoneNumber(getPhoneNumber())
				.address(getAddress())
				.openingHours(getOpeningHours())
				.url(url)
				.holidays(getHolidays())
				.build();
	}

	private String getRealId() {
		Pattern realIdPatternInUrl = Pattern.compile("sn=(.+?)(?=&)");
		Matcher matcher = realIdPatternInUrl.matcher(url);
		if (!matcher.find()) {
			throw new IllegalArgumentException("고유 id가 존재하지 않습니다.");
		}
		return matcher.group(1);
	}

	private String getRegion() {
		return CrawlUtil.getRegionFromAddress(getAddress());
	}

	private String getBranchName() {
		return page.select(".type > .name").text();
	}

	private String getPhoneNumber() {
		return page.select(".table.table-bordered > tbody > tr > td").eq(2).text();
	}

	private String getAddress() {
		return page.select(".table.table-bordered > tbody > tr > td").eq(0).text();
	}

	private String getOpeningHours() {
		return page.select(".table.table-bordered > tbody > tr > td").eq(3).text();
	}

	private List<Holiday> getHolidays() {
		String holidayText = page.select(".col-lg-4.time > span.off").text();
		System.out.println(holidayText);
		RegularHoliday regularHoliday = new RegularHoliday(holidayText);
		return regularHoliday.makeRegularHolidays();
	}
}
