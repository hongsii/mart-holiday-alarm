package com.hongsi.martholidayalarm.crawler.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LottemartCrawler implements MartCrawler {

	private int currentPage = 1;

	@Override
	public List<MartPage> crawl() throws IOException {
		List<MartPage> pages = new ArrayList<>();
		Elements linkTags;
		do {
			linkTags = getLinkTagsFromListPage();
			for (Element linkTag : linkTags) {
				pages.add(getPageFrom(linkTag));
			}
		} while (!isLastPage(linkTags));
		return pages;
	}

	private Elements getLinkTagsFromListPage() throws IOException {
		final String LINK_TAGS_SELECTOR = "ul.office_list > li > div.bx_type1 > div.article4 > ul > li:first-child > span > a";
		String url = LottemartPage.BASE_URL
				+ "/bc/branchSearch/branchSearch.do?schBrnchTypeCd=BC0701&currentPageNo="
				+ currentPage++;
		return Jsoup.connect(url).get().select(LINK_TAGS_SELECTOR);
	}

	private MartPage getPageFrom(Element linkTag) throws IOException {
		final String PAGE_LINK_ATTR = "href";
		return new LottemartPage(LottemartPage.BASE_URL + linkTag.attr(PAGE_LINK_ATTR));
	}

	private boolean isLastPage(Elements linkTags) {
		return linkTags.size() == 0;
	}

}
