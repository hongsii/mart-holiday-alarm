package com.hongsi.martholidayalarm.crawler.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.mart.domain.MartData;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.util.List;

public class MartCrawlerTest {

	protected void assertCrawledData(CrawlerMartType crawlerType, List<MartType> expectedMartType)
			throws Exception {
		Class<? extends MartCrawler> crawlerClass = crawlerType.getMartCrawler();
		MartCrawler crawler = crawlerClass.newInstance();

		List<? extends MartData> marts = crawler.crawl();
		assertThat(marts).isNotEmpty();

		marts.stream().forEach(martData -> {
			System.out.println(martData);
			assertThat(martData.getMartType()).isIn(expectedMartType);
			assertThat(martData.getRealId()).isNotBlank();
			assertThat(martData.getBranchName()).isNotBlank();
			assertThat(martData.getRegion()).isNotBlank();
			assertThat(martData.getPhoneNumber()).isNotBlank();
			assertThat(martData.getAddress()).isNotBlank();
			assertThat(martData.getOpeningHours()).isNotBlank();
		});
	}
}
