package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EmartParser extends Parser {

	public static final String LIST_URL = "http://store.emart.com/branch/list.do";
	public static final String DETAIL_PAGE_URL = "http://store.emart.com/branch/view.do?id=";

	@Override
	public List<Mart> getParsedData() throws IOException {
		List<Mart> marts = new ArrayList();
		Document listPage = Jsoup.connect(LIST_URL).get();
		Elements links = findLinksFrom(listPage);
		for (Element link : links) {
			if (isEmart(link)) {
				String realId = findRealId(link);
				Document detailPage = Jsoup
						.connect(DETAIL_PAGE_URL + realId).get();
				Mart mart = Mart.builder()
						.martType(MartType.EMART)
						.realId(realId)
						.region(findRegion(link))
						.branchName(findBranchName(detailPage))
						.phoneNumber(findPhoneNumber(detailPage))
						.address(findAddress(detailPage))
						.build();
				mart.addHolidays(findHolidaysFrom(detailPage, mart));
				marts.add(mart);
			}
		}
		return marts;
	}

	private Elements findLinksFrom(Document document) {
		return document.select("ul.d-tab > li > div > ul > li > a");
	}

	private boolean isEmart(Element link) {
		return MartType.EMART.getType().equals(link.attr("data-etc"));
	}

	private String findRealId(Element link) {
		return link.attr("data-code");
	}

	private String findRegion(Element link) {
		return link.parent().parent().parent().parent().select("a[href=\"#\"]").text();
	}

	private String findBranchName(Document detailPage) {
		return detailPage.select("h2").text();
	}

	private String findPhoneNumber(Document detailPage) {
		return detailPage.select("li.num > dl > dd").first().text();
	}

	private String findAddress(Document detailPage) {
		return detailPage.select("li.addr").first().childNode(0).outerHtml().trim();
	}

	private List<Holiday> findHolidaysFrom(Document detailPage, Mart mart) {
		Elements holidayTags = detailPage.select("div.calendar_table.d-calendar");
		List<Holiday> holidays = new ArrayList<>();
		for (Element holidayTag : holidayTags) {
			String[] holidayTexts = holidayTag.attr("data-holidays").replaceAll("\\[|\\]|\\'", "")
					.split(",");
			for (int i = 0; i < holidayTexts.length; i++) {
				String holidayText = holidayTexts[i].trim();
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
