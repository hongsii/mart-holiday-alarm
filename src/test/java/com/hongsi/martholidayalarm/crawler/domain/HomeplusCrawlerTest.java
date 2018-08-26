package com.hongsi.martholidayalarm.crawler.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;

public class HomeplusCrawlerTest {

	private HomeplusCrawler homeplusCrawler = new HomeplusCrawler();

	@Test
	public void getPages() throws Exception {
		List<MartPage> pages = homeplusCrawler.crawl();
		assertThat(pages.size()).isGreaterThan(0);
	}
}