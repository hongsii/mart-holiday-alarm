package com.hongsi.martholidayalarm.crawler.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;

public class HomeplusExpressCrawlerTest {

	private HomeplusExpressCrawler homeplusExpressCrawler = new HomeplusExpressCrawler();

	@Test
	public void getPages() throws Exception {
		List<MartPage> pages = homeplusExpressCrawler.crawl();
		assertThat(pages.size()).isGreaterThan(0);
	}
}