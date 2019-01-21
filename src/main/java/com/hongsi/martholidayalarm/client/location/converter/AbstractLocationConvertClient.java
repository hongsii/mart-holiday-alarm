package com.hongsi.martholidayalarm.client.location.converter;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

public abstract class AbstractLocationConvertClient implements LocationConvertClient {

	private final RestTemplate restTemplate;
	protected final LocationConverterClientInfo locationConverterClientInfo;

	protected AbstractLocationConvertClient(RestTemplateBuilder restTemplateBuilder, LocationConverterClientInfo locationConverterClientInfo) {
		this.restTemplate = restTemplateBuilder.build();
		this.locationConverterClientInfo = locationConverterClientInfo;
	}

	protected <T> T sendRequest(UriComponents uriComponents, HttpEntity<?> httpEntity, Class<T> responseType) {
		return restTemplate
				.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, responseType)
				.getBody();
	}

	public void setSearchUrl(String url) {
		locationConverterClientInfo.setSearchUrl(url);
	}
}
