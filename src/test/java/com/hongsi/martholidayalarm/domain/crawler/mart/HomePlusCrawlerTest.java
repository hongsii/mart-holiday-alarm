package com.hongsi.martholidayalarm.domain.crawler.mart;

import static java.util.Arrays.asList;

import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartType;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import org.junit.Ignore;
import org.junit.Test;

public class HomePlusCrawlerTest extends MartCrawlerTest {

	@Test
	@Ignore
	public void crawl() {
		assertCrawledData(CrawlerMartType.HOMEPLUS, asList(MartType.HOMEPLUS, MartType.HOMEPLUS_EXPRESS));
	}
}