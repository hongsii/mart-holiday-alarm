package com.hongsi.martholidayalarm.crawler.domain;

import java.util.List;

public interface MartCrawler {

	List<MartPage> crawl() throws Exception;
}
