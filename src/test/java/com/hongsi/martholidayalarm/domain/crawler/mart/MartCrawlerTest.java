package com.hongsi.martholidayalarm.domain.crawler.mart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.crawler.AbstractMartCrawler;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartData;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartType;
import com.hongsi.martholidayalarm.domain.crawler.MartCrawler;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import java.lang.reflect.Constructor;
import java.util.List;
import org.mockito.stubbing.Answer;

public class MartCrawlerTest {

	private LocationConvertClient locationConvertClient = mock(LocationConvertClient.class);

	protected void assertCrawledData(CrawlerMartType crawlerType, List<MartType> expectedMartType) {
		Class<AbstractMartCrawler> martCrawler = (Class<AbstractMartCrawler>) crawlerType.getMartCrawler();
		assertCrawledData(newInstanceAndMocking(martCrawler), expectedMartType);
	}

	protected void assertCrawledData(MartCrawler martCrawler, List<MartType> expectedMartType) {
		when(locationConvertClient.convert(any())).thenAnswer((Answer) invocation -> new LocationConvertResult());

		List<CrawlerMartData> marts = martCrawler.crawl();

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

	private MartCrawler newInstanceAndMocking(Class<AbstractMartCrawler> crawlerType) {
		try {
			Constructor<AbstractMartCrawler> constructor = crawlerType.getConstructor(
					LocationConvertClient.class);
			return constructor.newInstance(locationConvertClient);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
