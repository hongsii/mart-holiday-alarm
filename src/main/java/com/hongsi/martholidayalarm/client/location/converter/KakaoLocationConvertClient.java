package com.hongsi.martholidayalarm.client.location.converter;

import com.hongsi.martholidayalarm.client.location.converter.dto.KakaoLocationSearchResult;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class KakaoLocationConvertClient extends AbstractLocationConvertClient {

	private static final int RESULT_PAGE_NO = 1;
	private static final int SIZE_PER_PAGE = 5;

	private HttpEntity<?> requestHttpEntity;

	public KakaoLocationConvertClient(RestTemplateBuilder restTemplateBuilder, LocationConverterClientInfo kakaoClientInfo) {
		super(restTemplateBuilder, kakaoClientInfo);

		requestHttpEntity = makeHttpEntityFromInfo();
	}

	private HttpEntity<?> makeHttpEntityFromInfo() {
		String appKeyFormat = "KakaoAK " + locationConverterClientInfo.getClientId();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", appKeyFormat);
		return new HttpEntity<>(httpHeaders);
	}

	@Override
	public LocationConvertResult convert(CrawlerMartData crawlerMartData) {
		String query = makeQuery(crawlerMartData);
		try {
			return sendRequest(
					makeRequestUri(locationConverterClientInfo.getSearchUrl(), query),
					requestHttpEntity, KakaoLocationSearchResult.class
			).findLocationConvertResult();
		} catch (Exception e) {
			log.warn("[WARNING][CRAWLING][LOCATION] - message : {}, MartType : {} / Branch Name : {}",
					e.getMessage(), crawlerMartData.getMartType(), crawlerMartData.getBranchName());
			return convertToLocation(crawlerMartData);
		}
	}

	private LocationConvertResult convertToLocation(CrawlerMartData crawlerMartData) {
		try {
			return sendRequest(
					makeRequestUri(locationConverterClientInfo.getAddressUrl(), crawlerMartData.getAddress()),
					requestHttpEntity, KakaoLocationSearchResult.class
			).findLocationConvertResult();
		} catch (Exception e) {
			log.error("[ERROR][CRAWLING][LOCATION] - message : {}, address : {}", e.getMessage(), crawlerMartData.getAddress());
			return new LocationConvertResult();
		}
	}

	public String makeQuery(CrawlerMartData crawlerMartData) {
		return String.format("%s %s", crawlerMartData.getMartType().getDisplayName(), crawlerMartData.getBranchName());
	}

	public UriComponents makeRequestUri(String url, String query) {
		return UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("query", query)
				.queryParam("page", RESULT_PAGE_NO)
				.queryParam("size", SIZE_PER_PAGE)
				.build();
	}
}
