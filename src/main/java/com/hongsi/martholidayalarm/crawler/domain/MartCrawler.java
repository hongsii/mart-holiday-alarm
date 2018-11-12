package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.crawler.exception.CrawlerNotFoundException;
import java.util.List;

public interface MartCrawler {

	static Class<? extends MartCrawler> of(MartType martType) throws CrawlerNotFoundException {
		switch (martType) {
			case EMART:
				return EmartCrawler.class;
			case LOTTEMART:
				return LottemartCrawler.class;
			case HOMEPLUS:
				return HomeplusCrawler.class;
			case HOMEPLUS_EXPRESS:
				return HomeplusExpressCrawler.class;
		}
		throw new CrawlerNotFoundException();
	}

	List<MartPage> crawl() throws Exception;
}
