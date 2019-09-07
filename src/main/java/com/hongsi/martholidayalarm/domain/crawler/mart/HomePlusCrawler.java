package com.hongsi.martholidayalarm.domain.crawler.mart;

import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartType;
import com.hongsi.martholidayalarm.domain.crawler.MartCrawler;
import com.hongsi.martholidayalarm.domain.mart.Crawlable;
import com.hongsi.martholidayalarm.exception.PageNotFoundException;
import com.hongsi.martholidayalarm.utils.HtmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class HomePlusCrawler implements MartCrawler {

	private static final String LIST_URL = CrawlerMartType.HOMEPLUS
			.appendUrl("/STORE/HyperMarket.aspx");
	private static final String MART_LINK_SELECTOR = ".type > .name > a";
	private static final String MART_LINK_ATTR_KEY = "href";

	@Override
	public List<Crawlable> crawl() {
		return HtmlParser.post(LIST_URL, getRequestParams())
				.select(MART_LINK_SELECTOR).stream()
				.map(element -> element.attr(MART_LINK_ATTR_KEY).trim())
				.map(this::parseMartData)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	private Map<String, String> getRequestParams() {
		Map<String, String> params = new HashMap<>();
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__LASTFOCUS", "");
		params.put("__VIEWSTATE",
				"/wEPDwUJLTc2MDkzMDI3D2QWAmYPZBYCAgUPZBYCAgEPZBYCAgEPEGRkFgFmZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAwUkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRzdG9yZXR5cGUxBSRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHN0b3JldHlwZTIFJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkc3RvcmV0eXBlM+aYO9PJofU5uQQJJZRZ2bboir3I");
		params.put("ctl00$ContentPlaceHolder1$Region_Code", "");
		params.put("ctl00$ContentPlaceHolder1$srch_name", "");
		params.put("ctl00$ContentPlaceHolder1$Button1", "");
		params.put("ctl00$ContentPlaceHolder1$storetype1", "on"); // HomePlus
		params.put("ctl00$ContentPlaceHolder1$storetype2", "on"); // Express
		return params;
	}

	private Optional<HomePlusData> parseMartData(String suffixUrl) {
		try {
			String url = CrawlerMartType.HOMEPLUS.appendUrl(suffixUrl);
			return Optional.of(new HomePlusData(url));
		} catch (PageNotFoundException e) {
			log.error("[ERROR][CRAWLING][PARSE] - message : {}, url : {}", e.getMessage(),
					suffixUrl);
			return Optional.empty();
		}
	}
}
