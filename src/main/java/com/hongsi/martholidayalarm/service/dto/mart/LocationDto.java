package com.hongsi.martholidayalarm.service.dto.mart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hongsi.martholidayalarm.domain.mart.Location;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDto {

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
		    return Optional.ofNullable(distance).orElse(DEFAULT_DISTANCE);
		}

		public Location toEntity() {
			return Location.of(latitude, longitude);
		}
	}

	@Getter
	@Setter
	public static class Response {

		private static final Response EMPTY = Response.builder().build();

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
				return EMPTY;
			}
			return Response.builder()
					.latitude(location.getLatitude())
					.longitude(location.getLongitude())
					.distance(location.getDistance())
					.build();
		}
	}
}
