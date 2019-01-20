package com.hongsi.martholidayalarm.domain.crawler.mart;

import static java.util.Arrays.asList;

import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartType;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import org.junit.Ignore;
import org.junit.Test;

public class LotteMartCrawlerTest extends MartCrawlerTest {

	@Test
	@Ignore
	public void crawl() throws Exception {
		assertCrawledData(CrawlerMartType.LOTTEMART, asList(MartType.LOTTEMART));
	}
}