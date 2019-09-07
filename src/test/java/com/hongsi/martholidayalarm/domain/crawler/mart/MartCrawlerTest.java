package com.hongsi.martholidayalarm.domain.crawler.mart;

import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartType;
import com.hongsi.martholidayalarm.domain.crawler.MartCrawler;
import com.hongsi.martholidayalarm.domain.mart.Crawlable;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MartCrawlerTest {

	private LocationConvertClient locationConvertClient = mock(LocationConvertClient.class);

	protected void assertCrawledData(CrawlerMartType crawlerType, List<MartType> expectedMartType) {
		Class<MartCrawler> martCrawler = (Class<MartCrawler>) crawlerType.getMartCrawler();
		assertCrawledData(newInstanceAndMocking(martCrawler), expectedMartType);
	}

	protected void assertCrawledData(MartCrawler martCrawler, List<MartType> expectedMartType) {
		when(locationConvertClient.convert(any())).thenAnswer((Answer) invocation -> new LocationConvertResult());

		List<Crawlable> marts = martCrawler.crawl();

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
			assertThat(martData.getHolidayText()).isNotNull();
		});
	}

	private MartCrawler newInstanceAndMocking(Class<MartCrawler> crawlerType) {
		try {
			return crawlerType.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
