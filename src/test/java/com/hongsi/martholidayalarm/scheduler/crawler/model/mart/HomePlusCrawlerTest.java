package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import org.junit.Ignore;
import org.junit.Test;

import static java.util.Arrays.asList;

public class HomePlusCrawlerTest extends MartCrawlerTest {

	@Test
	@Ignore
	public void crawl() {
		assertCrawledData(CrawlerMartType.HOMEPLUS, asList(MartType.HOMEPLUS, MartType.HOMEPLUS_EXPRESS));
	}
}