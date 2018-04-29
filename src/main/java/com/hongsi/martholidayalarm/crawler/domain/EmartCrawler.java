package com.hongsi.martholidayalarm.crawler.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EmartCrawler extends MartCrawler {

	@Override
	public List<MartPage> crawl() throws IOException {
		List<MartPage> pages = new ArrayList<>();
		Document listPage = Jsoup.connect(EmartPage.getListUrl()).get();
		Elements linkTags = getEmartLinkTags(listPage);
		for (Element linkTag : linkTags) {
			String url = getCode(linkTag);
			Document detailPage = Jsoup.connect(url).get();
			MartPage page = new EmartPage(url, detailPage);
			pages.add(page);
		}
		return pages;
	}

	private Elements getEmartLinkTags(Document document) {
		return document.select(".tab_cont > ul > li > a[data-etc=\"E\"");
	}

	private String getCode(Element linkTag) {
		return EmartPage.getPageUrlForCrawling() + linkTag.attr("data-code");
	}
}
