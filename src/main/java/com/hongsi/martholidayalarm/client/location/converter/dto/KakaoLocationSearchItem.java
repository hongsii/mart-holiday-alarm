package com.hongsi.martholidayalarm.client.location.converter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.annotation.ExternalJsonItem;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.utils.ValidationUtils;

@ExternalJsonItem
public class KakaoLocationSearchItem {

	@JsonProperty("road_address_name")
	private String roadAddress;
	@JsonProperty("address_name")
	private String address;
	@JsonProperty("y")
	private Double latitude;
	@JsonProperty("x")
	private Double longitude;

	public String getAddress() {
		if (ValidationUtils.isNotBlank(roadAddress)) {
			return roadAddress;
		}
		return address;
	}

	public Location getLocation() {
		return Location.of(latitude, longitude);
	}
}
