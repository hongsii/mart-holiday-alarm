package com.hongsi.martholidayalarm.client.location.converter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationConversion {

	public static final LocationConversion EMPTY = new LocationConversion();

	private String address;
	private Double latitude;
	private Double longitude;

	public LocationConversion(String address, Double latitude, Double longitude) {
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getAddressOrDefault(String defaultAddress) {
		return StringUtils.hasText(address) ? address : defaultAddress;
	}
}
