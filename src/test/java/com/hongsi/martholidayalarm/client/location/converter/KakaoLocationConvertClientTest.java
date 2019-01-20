package com.hongsi.martholidayalarm.client.location.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.config.CrawlerConfig;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartData;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponents;

@RunWith(SpringRunner.class)
@RestClientTest(KakaoLocationConvertClient.class)
@Import(CrawlerConfig.class)
public class KakaoLocationConvertClientTest {

	@Autowired
	private KakaoLocationConvertClient kakaoAddressClient;
	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	private String searchUrl = "https://dapi.kakao.com/v2/local/convert/keyword.json";

	@Before
	public void setUp() throws Exception {
		kakaoAddressClient.setSearchUrl(searchUrl);
	}

	@Test
	public void 파싱_확인() {
		CrawlerMartData crawlerMartData = CrawlerMartData.builder()
				.martType(MartType.HOMEPLUS_EXPRESS)
				.branchName("잠실점")
				.build();
		UriComponents uriComponents = kakaoAddressClient.makeRequestUri(searchUrl, kakaoAddressClient.makeQuery(crawlerMartData));
		String expected = "{ \"documents\": [ { \"address_name\": \"서울 송파구 잠실동 44-1\", \"category_group_code\": \"mt1\", \"category_group_name\": \"대형마트\", \"category_name\": \"가정,생활 > 슈퍼마켓 > 대형슈퍼 > 홈플러스익스프레스\", \"distance\": \"\", \"id\": \"16705428\", \"phone\": \"02-418-8545\", \"place_name\": \"홈플러스익스프레스 잠실점\", \"place_url\": \"http://place.map.daum.net/16705428\", \"road_address_name\": \"서울 송파구 석촌호수로 133\", \"x\": \"127.09182075113635\", \"y\": \"37.507577456249656\" } ], \"meta\": { \"is_end\": true, \"pageable_count\": 1, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 잠실점\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 1 } }";
		mockRestServiceServer.expect(requestTo(uriComponents.encode().toUriString()))
				.andRespond(withSuccess(expected, MediaType.APPLICATION_JSON_UTF8));

		LocationConvertResult response = kakaoAddressClient.convert(crawlerMartData);

		assertThat(response.getLocation()).isEqualTo(Location.of(37.507577456249656, 127.09182075113635));
	}
}