package com.hongsi.martholidayalarm.crawler.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class EmartCrawler implements MartCrawler {

	@Override
	public List<MartPage> crawl() throws IOException {
		List<MartPage> pages = new ArrayList<>();
		Elements linkTags = getLinkTagsFromListPage();
		for (Element linkTag : linkTags) {
			try {
				pages.add(getPageFrom(linkTag));
			} catch (IOException ie) {
				log.error("Can not access this page : " + ie.getMessage());
			}
		}
		return pages;
	}

	private Elements getLinkTagsFromListPage() throws IOException {
		final String LINK_TAGS_SELECTOR = ".tab_cont > ul > li > a[data-etc=\"E\"";
		String url = EmartPage.LIST_URL;
		return Jsoup.connect(url).get().select(LINK_TAGS_SELECTOR);
	}

	private MartPage getPageFrom(Element linkTag) throws IOException {
		final String PAGE_ID_ATTR = "data-code";
		String url = EmartPage.CRAWLING_PAGE_URL + linkTag.attr(PAGE_ID_ATTR);
		return new EmartPage(url);
	}
}
