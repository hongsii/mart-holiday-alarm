package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.crawler.exception.PageNotFoundException;
import com.hongsi.martholidayalarm.crawler.util.CrawlUtil;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeplusPage extends MartPage {

	public static final String BASE_URL = "http://corporate.homeplus.co.kr";

	private MartType martType;

	HomeplusPage(MartType martType, String url) throws Exception {
		super(url);
		// 홈플러스는 페이지가 존재하지 않으면 리다이렉션되기 때문에 가져온 페이지 내 정보가 있는지 확인
		if (page.body().text().isEmpty()) {
			throw new PageNotFoundException();
		}
		this.martType = martType;
	}

	@Override
	public Mart getInfo() {
		return Mart.builder()
				.martType(martType)
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
		String holidayText = page.select(".time > span.off").text();
		RegularHoliday regularHoliday = new RegularHoliday(holidayText);
		return regularHoliday.makeRegularHolidays();
	}
}
