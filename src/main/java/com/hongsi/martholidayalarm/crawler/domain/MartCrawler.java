package com.hongsi.martholidayalarm.crawler.domain;

import java.io.IOException;
import java.util.List;

public interface MartCrawler {

	List<MartPage> crawl() throws IOException;
}
