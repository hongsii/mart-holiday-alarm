package com.hongsi.martholidayalarm.client.location.converter;

import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawledMart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(KakaoLocationConvertClient.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class KakaoLocationConvertClientTest {

	private static final String searchUrl = "https://dapi.kakao.com/v2/local/convert/keyword.json";
	private static final String addressUrl = "https://dapi.kakao.com/v2/local/search/address.json";

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	private LocationConvertClient locationConvertClient;

	@Before
	public void setUp() {
		LocationConvertClientInfo clientInfo = LocationConvertClientInfo.builder()
				.clientId("test")
				.searchUrl(searchUrl)
				.addressUrl(addressUrl)
				.build();
		locationConvertClient = new KakaoLocationConvertClient(restTemplateBuilder, clientInfo);
	}

	@Test
	public void convertToName() {
		String expected = "{ \"documents\": [ { \"address_name\": \"서울 송파구 잠실동 44-1\", \"category_group_code\": \"mt1\", \"category_group_name\": \"대형마트\", \"category_name\": \"가정,생활 > 슈퍼마켓 > 대형슈퍼 > 홈플러스익스프레스\", \"distance\": \"\", \"id\": \"16705428\", \"phone\": \"02-418-8545\", \"place_name\": \"홈플러스익스프레스 잠실점\", \"place_url\": \"http://place.map.daum.net/16705428\", \"road_address_name\": \"서울 송파구 석촌호수로 133\", \"x\": \"127.09182075113635\", \"y\": \"37.507577456249656\" } ], \"meta\": { \"is_end\": true, \"pageable_count\": 1, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 잠실점\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 1 } }";
		mockRestServiceServer.expect(requestTo(getRequestUri(searchUrl, "홈플러스 익스프레스 잠실점")))
				.andRespond(withSuccess(expected, MediaType.APPLICATION_JSON_UTF8));

		CrawledMart crawledMart = CrawledMart.builder()
				.martType(MartType.HOMEPLUS_EXPRESS)
				.branchName("잠실점")
				.build();
		LocationConvertResult response = locationConvertClient.convert(crawledMart);

		assertThat(response.getLocation()).isEqualTo(Location.of(37.507577456249656, 127.09182075113635));
	}

	@Test
	public void convertToAddressAfterNameConversionFailure() {
		String failResponse = "{ \"documents\": [], \"meta\": { \"is_end\": true, \"pageable_count\": 0, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 없는 마트\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 0 } }\n";
		mockRestServiceServer.expect(ExpectedCount.times(1), requestTo(getRequestUri(searchUrl, "홈플러스 익스프레스 없는 마트")))
				.andRespond(withSuccess(failResponse, MediaType.APPLICATION_JSON_UTF8));
		String expected = "{ \"documents\": [ { \"address_name\": \"서울 송파구 잠실동 44-1\", \"category_group_code\": \"mt1\", \"category_group_name\": \"대형마트\", \"category_name\": \"가정,생활 > 슈퍼마켓 > 대형슈퍼 > 홈플러스익스프레스\", \"distance\": \"\", \"id\": \"16705428\", \"phone\": \"02-418-8545\", \"place_name\": \"홈플러스익스프레스 잠실점\", \"place_url\": \"http://place.map.daum.net/16705428\", \"road_address_name\": \"서울 송파구 석촌호수로 133\", \"x\": \"127.09182075113635\", \"y\": \"37.507577456249656\" } ], \"meta\": { \"is_end\": true, \"pageable_count\": 1, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 잠실점\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 1 } }";
		mockRestServiceServer.expect(ExpectedCount.times(1), requestTo(getRequestUri(addressUrl, "서울시 구로구 우리집")))
				.andRespond(withSuccess(expected, MediaType.APPLICATION_JSON_UTF8));

		CrawledMart crawledMart = CrawledMart.builder()
				.martType(MartType.HOMEPLUS_EXPRESS)
				.branchName("없는 마트")
				.address("서울시 구로구 우리집")
				.build();
		LocationConvertResult response = locationConvertClient.convert(crawledMart);

		assertThat(response.getLocation()).isEqualTo(Location.of(37.507577456249656, 127.09182075113635));
	}

	@Test
	public void convertAllFailure() {
		String expected = "{ \"documents\": [], \"meta\": { \"is_end\": true, \"pageable_count\": 0, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 없는 마트\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 0 } }\n";
		mockRestServiceServer.expect(requestTo(getRequestUri(searchUrl, "홈플러스 익스프레스 없는 마트")))
				.andRespond(withSuccess(expected, MediaType.APPLICATION_JSON_UTF8));
		mockRestServiceServer.expect(requestTo(getRequestUri(addressUrl, "서울시 구로구 우리집")))
				.andRespond(withSuccess(expected, MediaType.APPLICATION_JSON_UTF8));

		CrawledMart crawledMart = CrawledMart.builder()
				.martType(MartType.HOMEPLUS_EXPRESS)
				.branchName("없는 마트")
				.address("서울시 구로구 우리집")
				.build();
		LocationConvertResult response = locationConvertClient.convert(crawledMart);

		assertThat(response).isEqualTo(new LocationConvertResult());
	}

	private String getRequestUri(String requestUrl, String query) {
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(requestUrl)
				.queryParam("query", query)
				.queryParam("page", 1)
				.queryParam("size", 5)
				.build();
		return uriComponents.encode().toString();
	}
}