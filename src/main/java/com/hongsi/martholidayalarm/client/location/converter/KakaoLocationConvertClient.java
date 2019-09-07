package com.hongsi.martholidayalarm.client.location.converter;

import com.hongsi.martholidayalarm.client.location.converter.dto.KakaoLocationSearchResult;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class KakaoLocationConvertClient extends AbstractLocationConvertClient {

	private HttpEntity<?> requestHttpEntity;

	public KakaoLocationConvertClient(RestTemplateBuilder restTemplateBuilder,
									  LocationConvertClientInfo kakaoClientInfo) {
		super(restTemplateBuilder, kakaoClientInfo);

		requestHttpEntity = makeHttpEntityFromInfo();
	}

	private HttpEntity<?> makeHttpEntityFromInfo() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "KakaoAK " + locationConvertClientInfo.getClientId());
		return new HttpEntity<>(httpHeaders);
	}

	@Override
	public LocationConvertResult convert(CrawlerMartData crawlerMartData) {
		String query = makeQuery(crawlerMartData);
		try {
			return requestToApi(locationConvertClientInfo.getSearchUrl(), query);
		} catch (Exception e) {
			log.warn("[CRAWLING][LOCATION] - query : {} / message : {}", query, e.getMessage());
			return convertToAddress(crawlerMartData);
		}
	}

	private String makeQuery(CrawlerMartData crawlerMartData) {
		return String.format("%s %s", crawlerMartData.getMartType().getDisplayName(), crawlerMartData.getBranchName());
	}

	private LocationConvertResult convertToAddress(CrawlerMartData crawlerMartData) {
		try {
			return requestToApi(locationConvertClientInfo.getAddressUrl(), crawlerMartData.getAddress());
		} catch (Exception e) {
			log.error("[CRAWLING][LOCATION] - address : {} / message : {}", crawlerMartData.getAddress(), e.getMessage());
			return new LocationConvertResult();
		}
	}

	private LocationConvertResult requestToApi(String requestUrl, String query) {
		KakaoLocationSearchResult kakaoLocationSearchResult = sendRequest(
				UriComponentsBuilder.fromHttpUrl(requestUrl)
						.queryParam("query", query)
						.queryParam("page", 1)
						.queryParam("size", 5)
						.build(),
				requestHttpEntity,
				KakaoLocationSearchResult.class
		);
		return kakaoLocationSearchResult.findLocationConvertResult();
	}
}
