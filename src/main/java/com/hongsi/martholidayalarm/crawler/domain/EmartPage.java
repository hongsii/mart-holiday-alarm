package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.crawler.util.CrawlUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EmartPage extends MartPage {

	public static final String BASE_URL = "http://store.emart.com";
	public static final String VIEW_PAGE_URL = BASE_URL + "/branch/list.do?id=";

	EmartPage(String url) throws IOException {
		super(url);
	}

	@Override
	public Mart getInfo() {
		return Mart.builder()
				.martType(MartType.EMART)
				.realId(getRealId())
				.region(getRegion())
				.branchName(getBranchName())
				.phoneNumber(getPhoneNumber())
				.address(getAddress())
				.openingHours(getOpeningHours())
				.url(getPageUrl())
				.holidays(getHolidays())
				.build();
	}


	private String getRealId() {
		return page.select("a[data-store-id]").attr("data-store-id");
	}

	private String getRegion() {
		return CrawlUtil.getRegionFromAddress(getAddress());
	}

	private String getBranchName() {
		return page.select("h2").text();
	}

	private String getPhoneNumber() {
		return page.select("li.num > dl > dd").first().text();
	}

	private String getAddress() {
		return page.select("li.addr > div.layer_addr").text();
	}

	private String getOpeningHours() {
		return page.select("li.time dd").text();
	}

	private String getPageUrl() {
		return VIEW_PAGE_URL + getRealId();
	}

	private List<Holiday> getHolidays() {
		List<Holiday> holidays = new ArrayList<>();
		Elements holidayTags = page.select("div.calendar_table.d-calendar");
		for (Element holidayTag : holidayTags) {
			String[] holidayTexts = holidayTag.attr("data-holidays").replaceAll("\\[|\\]|'", "")
					.split(",");
			for (String holidayText : holidayTexts) {
				holidayText = holidayText.trim();
				if (!holidayText.isEmpty()) {
					holidays.add(Holiday.builder()
							.date(LocalDate.parse(holidayText))
							.build());
				}
			}
		}
		return holidays;
	}
}
