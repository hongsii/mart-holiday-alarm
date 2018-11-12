package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HomeplusCrawler implements MartCrawler {

	@Override
	public List<MartPage> crawl() throws Exception {
		List<MartPage> pages = new ArrayList<>();
		for (Element linkTag : getLinkTags()) {
			try {
				pages.add(getPageFrom(linkTag));
			} catch (Exception e) {
				log.error("[Page Crawling Error - Homeplus] message : {} / link : {}",
						linkTag, e.getMessage());
			}
		}
		return pages;
	}

	private Elements getLinkTags() throws Exception {
		// 검색시 사용하는 form 설정 및 POST 요청
		return Jsoup.connect(HomeplusPage.BASE_URL + "/STORE/HyperMarket.aspx")
				.data("__EVENTTARGET", "")
				.data("__EVENTARGUMENT", "")
				.data("__LASTFOCUS", "")
				.data("__VIEWSTATE",
						"/wEPDwUJLTc2MDkzMDI3D2QWAmYPZBYCAgUPZBYCAgEPZBYCAgEPEGRkFgFmZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAwUkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRzdG9yZXR5cGUxBSRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHN0b3JldHlwZTIFJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkc3RvcmV0eXBlM+aYO9PJofU5uQQJJZRZ2bboir3I")
				.data("ctl00$ContentPlaceHolder1$Region_Code", "")
				.data("ctl00$ContentPlaceHolder1$srch_name", "")
				.data("ctl00$ContentPlaceHolder1$Button1", "")
				.data("ctl00$ContentPlaceHolder1$storetype1", "on")
				.post()
				.select(".type > .name > a");
	}

	private MartPage getPageFrom(Element linkTag) throws Exception {
		String url = HomeplusPage.BASE_URL + linkTag.attr("href").trim();
		return new HomeplusPage(MartType.HOMEPLUS, url);
	}
}
