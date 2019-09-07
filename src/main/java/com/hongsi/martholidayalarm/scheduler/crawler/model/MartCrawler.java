package com.hongsi.martholidayalarm.scheduler.crawler.model;

import com.hongsi.martholidayalarm.scheduler.crawler.model.mart.Crawlable;

import java.util.List;

public interface MartCrawler {

	List<Crawlable> crawl();
}
