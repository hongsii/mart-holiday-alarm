package com.hongsi.martholidayalarm.service.dto.mart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hongsi.martholidayalarm.domain.mart.Location;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDto {

	@Getter
	@Setter
	public static class Request {

		private static final int DEFAULT_DISTANCE = 3;

		@NotNull
		private Double latitude;
		@NotNull
		private Double longitude;
		private Integer distance;

		public Request(Double latitude, Double longitude, Integer distance) {
			this.latitude = latitude;
			this.longitude = longitude;
			this.distance = distance;
		}

		public int getDistance() {
			if (distance == null) {
				return DEFAULT_DISTANCE;
			}
			return distance;
		}

		public Location toEntity() {
			return Location.of(latitude, longitude);
		}

		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}

		public void setDistance(Integer distance) {
			this.distance = distance;
		}
	}

	@Getter
	@Setter
	public static class Response {

		private Double latitude;
		private Double longitude;
		@JsonInclude(Include.NON_NULL)
		private Double distance;

		@Builder
		private Response(Double latitude, Double longitude, Double distance) {
			this.latitude = latitude;
			this.longitude = longitude;
			this.distance = distance;
		}

		public static Response of(Location location) {
			if (location == null) {
				return Response.builder().build();
			}
			return Response.builder()
					.latitude(location.getLatitude())
					.longitude(location.getLongitude())
					.distance(location.getDistance())
					.build();
		}
	}
}
