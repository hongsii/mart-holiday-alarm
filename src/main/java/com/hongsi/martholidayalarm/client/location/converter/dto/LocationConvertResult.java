package com.hongsi.martholidayalarm.client.location.converter.dto;

import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.utils.ValidationUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationConvertResult {

	private String address;
	private Location location;

	public LocationConvertResult(String address, Location location) {
		this.address = address;
		this.location = location;
	}

	public String getAddress(String defaultAddress) {
		if (ValidationUtils.isNotBlank(address)) {
			return address;
		}
		return defaultAddress;
	}
}
