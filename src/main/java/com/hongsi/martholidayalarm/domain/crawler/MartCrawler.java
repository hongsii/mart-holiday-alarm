package com.hongsi.martholidayalarm.domain.crawler;

import com.hongsi.martholidayalarm.domain.mart.Crawlable;

import java.util.List;

public interface MartCrawler {

	List<Crawlable> crawl();
}
