package com.hongsi.martholidayalarm.crawler.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EmartCrawler implements MartCrawler {

	@Override
	public List<MartPage> crawl() throws IOException {
		List<MartPage> pages = new ArrayList<>();
		Elements linkTags = getLinkTagsFromListPage();
		for (Element linkTag : linkTags) {
			pages.add(getPageFrom(linkTag));
		}
		return pages;
	}

	private Elements getLinkTagsFromListPage() throws IOException {
		// 이마트, 트레이더스 조회
		final String LINK_TAGS_SELECTOR = ".tab_cont > ul > li > a[data-etc=\"E\"], a[data-etc=\"T\"], a[data-etc=\"A\"]";
		String url = EmartPage.BASE_URL + "/branch/list.do";
		return Jsoup.connect(url).get().select(LINK_TAGS_SELECTOR);
	}

	private MartPage getPageFrom(Element linkTag) throws IOException {
		final String PAGE_ID_ATTR = "data-code";
		String url = EmartPage.BASE_URL + "/branch/view.do?id=" + linkTag.attr(PAGE_ID_ATTR);
		return new EmartPage(url);
	}
}
