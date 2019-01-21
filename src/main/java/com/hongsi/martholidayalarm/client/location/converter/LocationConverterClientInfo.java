package com.hongsi.martholidayalarm.client.location.converter;

import lombok.Data;

@Data
public class LocationConverterClientInfo {

	private String clientId;
	private String clientSecret;
	private String searchUrl;
	private String addressUrl;
}
