package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EmartPage extends MartPage {

	private static final String BASE_URL = "http://store.emart.com";
	private static final String LIST_URL = "/branch/list.do";
	private static final String CRAWLING_PAGE_URL = "/branch/view.do?id=";
	private static final String VIEW_PAGE_URL = "/branch/list.do?id=";

	public EmartPage(String url, Document page) throws IOException {
		super(url, page);
	}

	public static String getListUrl() {
		return BASE_URL + LIST_URL;
	}

	public static String getPageUrlForCrawling() {
		return BASE_URL + CRAWLING_PAGE_URL;
	}

	public static String getPageUrlForView() {
		return BASE_URL + VIEW_PAGE_URL;
	}

	@Override
	public Mart getInfo() {
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId(getRealId())
				.region(getRegion())
				.branchName(getBranchName())
				.phoneNumber(getPhoneNumber())
				.address(getAddress())
				.url(getViewUrl())
				.build();
		mart.addHolidays(getHolidays(mart));
		return mart;
	}

	private String getViewUrl() {
		return getPageUrlForView() + getRealId();
	}

	private String getRealId() {
		return page.select("div.btn.d-favor-toggle > button").attr("data-store-id");
	}

	private String getRegion() {
		return getAddress().substring(0, 2);
	}

	private String getAddress() {
		return page.select("li.addr > div.layer_addr").text();
	}

	private String getBranchName() {
		return page.select("h2").text();
	}

	private String getPhoneNumber() {
		return page.select("li.num > dl > dd").first().text();
	}

	private List<Holiday> getHolidays(Mart mart) {
		List<Holiday> holidays = new ArrayList<>();
		Elements holidayTags = page.select("div.calendar_table.d-calendar");
		for (Element holidayTag : holidayTags) {
			String[] holidayTexts = holidayTag.attr("data-holidays").replaceAll("\\[|\\]|\\'", "")
					.split(",");
			for (String holidayText : holidayTexts) {
				holidayText = holidayText.trim();
				if (!holidayText.isEmpty()) {
					holidays.add(Holiday.builder()
							.date(LocalDate.parse(holidayText))
							.mart(mart)
							.build());
				}
			}
		}
		return holidays;
	}
}
