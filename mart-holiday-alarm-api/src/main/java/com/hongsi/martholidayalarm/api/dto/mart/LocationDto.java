package com.hongsi.martholidayalarm.api.dto.mart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hongsi.martholidayalarm.core.location.Location;
import lombok.Data;

import javax.annotation.Nullable;

@Data
public class LocationDto {

	private Double latitude;
	private Double longitude;
	@JsonInclude(Include.NON_NULL)
	private Double distance;

	public LocationDto(@Nullable Location location, @Nullable Double distance) {
		if (location != null) {
			this.latitude = location.getLatitude();
			this.longitude = location.getLongitude();
		}
		this.distance = distance;
	}
}
