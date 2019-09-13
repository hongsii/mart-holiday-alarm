package com.hongsi.martholidayalarm.client.location.converter;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

public abstract class AbstractLocationConvertClient implements LocationConvertClient {

	private final RestTemplate restTemplate;
	protected final LocationConvertClientInfo locationConvertClientInfo;

	protected AbstractLocationConvertClient(RestTemplateBuilder restTemplateBuilder, LocationConvertClientInfo locationConvertClientInfo) {
		this.restTemplate = restTemplateBuilder.build();
		this.locationConvertClientInfo = locationConvertClientInfo;
	}

	protected <T> T sendRequest(UriComponents uriComponents, HttpEntity<?> httpEntity, Class<T> responseType) {
		return restTemplate
				.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, responseType)
				.getBody();
	}
}
