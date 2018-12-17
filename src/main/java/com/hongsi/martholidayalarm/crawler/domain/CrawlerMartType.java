package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.crawler.domain.mart.emart.EmartCrawler;
import com.hongsi.martholidayalarm.crawler.domain.mart.homplus.HomePlusCrawler;
import com.hongsi.martholidayalarm.crawler.domain.mart.lottemart.LotteMartCrawler;

public enum CrawlerMartType {

	EMART(EmartCrawler.class, "http://store.emart.com"),
	LOTTEMART(LotteMartCrawler.class, "http://company.lottemart.com"),
	HOMEPLUS(HomePlusCrawler.class, "http://corporate.homeplus.co.kr");

	private static final String SLASH = "/";

	private Class<? extends MartCrawler> martCrawler;
	private String url;

	CrawlerMartType(Class<? extends MartCrawler> martCrawler, String url) {
		this.martCrawler = martCrawler;
		this.url = url;
	}

	public Class<? extends MartCrawler> getMartCrawler() {
		return martCrawler;
	}

	public String appendUrl(String suffix) {
		StringBuilder url = new StringBuilder(this.url);
		if (!suffix.startsWith(SLASH)) {
			url.append(SLASH);
		}
		return url.append(suffix).toString();
	}
}
