package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.crawler.model.costco.CostcoCrawler;
import com.hongsi.martholidayalarm.crawler.model.emart.EmartCrawler;
import com.hongsi.martholidayalarm.crawler.model.emart.EmartTradersCrawler;
import com.hongsi.martholidayalarm.crawler.model.emart.NobrandCrawler;
import com.hongsi.martholidayalarm.crawler.model.homeplus.HomePlusCrawler;
import com.hongsi.martholidayalarm.crawler.model.homeplus.HomePlusExpressCrawler;
import com.hongsi.martholidayalarm.crawler.model.lottemart.LotteMartCrawler;
import lombok.Getter;

public enum MartCrawlerType {

	EMART(EmartCrawler.class, "https://store.emart.com"),
	EMART_TRADERS(EmartTradersCrawler.class, "https://store.traders.co.kr"),
	NOBRAND(NobrandCrawler.class, EMART.url),
	LOTTEMART(LotteMartCrawler.class, "http://company.lottemart.com"),
	HOMEPLUS(HomePlusCrawler.class, "https://corporate.homeplus.co.kr"),
	HOMEPLUS_EXPRESS(HomePlusExpressCrawler.class, HOMEPLUS.url),
	COSTCO(CostcoCrawler.class, "https://www.costco.co.kr");

	private static final String SLASH = "/";

	@Getter
	private final Class<? extends MartCrawler> martCrawler;
	private final String url;

	MartCrawlerType(Class<? extends MartCrawler> martCrawler, String url) {
		this.martCrawler = martCrawler;
		this.url = url;
	}

	public String appendUrl(String suffix) {
		StringBuilder url = new StringBuilder(this.url);
		if (!suffix.startsWith(SLASH)) {
			url.append(SLASH);
		}
		return url.append(suffix).toString();
	}
}
