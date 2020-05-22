package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.crawler.model.MartParser;

import java.util.List;

public interface MartCrawler {

	List<? extends MartParser> crawl();
}
