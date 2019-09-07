package com.hongsi.martholidayalarm.client.location.converter;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationConvertClientInfo {

	private String clientId;
	private String clientSecret;
	private String searchUrl;
	private String addressUrl;

	@Builder
	public LocationConvertClientInfo(String clientId,
									 String clientSecret,
									 String searchUrl,
									 String addressUrl) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.searchUrl = searchUrl;
		this.addressUrl = addressUrl;
	}
}
