package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import org.junit.Ignore;
import org.junit.Test;

import static java.util.Arrays.asList;

public class EmartCrawlerTest extends MartCrawlerTest {

	@Test
	@Ignore
	public void crawl() throws Exception {
		assertCrawledData(CrawlerMartType.EMART, asList(MartType.EMART, MartType.EMART_TRADERS));
	}
}