package com.hongsi.martholidayalarm.crawler.domain.mart.lottemart;

import static java.util.Arrays.asList;

import com.hongsi.martholidayalarm.crawler.domain.CrawlerMartType;
import com.hongsi.martholidayalarm.crawler.domain.MartCrawlerTest;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import org.junit.Ignore;
import org.junit.Test;

public class LotteMartCrawlerTest extends MartCrawlerTest {

	@Test
	@Ignore
	public void crawl() throws Exception {
		assertCrawledData(CrawlerMartType.LOTTEMART, asList(MartType.LOTTEMART));
	}
}