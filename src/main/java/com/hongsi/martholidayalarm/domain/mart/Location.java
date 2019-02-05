package com.hongsi.martholidayalarm.domain.mart;

import com.hongsi.martholidayalarm.exception.LocationOutOfRangeException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Location {

	@Column(columnDefinition = "DECIMAL(10, 8)")
	private Double latitude;

	@Column(columnDefinition = "DECIMAL(11, 8)")
	private Double longitude;

	@Transient
	@Setter
	private Double distance;

	@Builder
	private Location(Double latitude, Double longitude, Double distance) {
		Range.Latitude.validate(latitude);
		Range.Longitude.validate(longitude);

		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}

	public static Location parse(String rawLatitude, String rawLongitude) {
		return Location.of(Double.valueOf(rawLatitude), Double.valueOf(rawLongitude));
	}

	public static Location of(Double latitude, Double longitude) {
		return Location.builder()
				.latitude(latitude)
				.longitude(longitude)
				.build();
	}

	public static Location of(Double latitude, Double longitude, Double distance) {
		return Location.builder()
				.latitude(latitude)
				.longitude(longitude)
				.distance(distance)
				.build();
	}

	@Getter
	public enum Range {

		Latitude("위도", -90, 90), Longitude("경도", -180, 180);

		private String name;
		private Double min;
		private Double max;

		Range(String name, int min, int max) {
			this.name = name;
			this.min = (double) min;
			this.max = (double) max;
		}

		public void validate(Double point) {
			if (Objects.isNull(point)) {
				throw new IllegalArgumentException("좌표를 반드시 입력해야합니다.");
			}
			if (isOutOfMinRange(point) || isOutOfMaxRange(point)) {
				throw new LocationOutOfRangeException(this);
			}
		}

		private boolean isOutOfMinRange(Double point) {
			return min > point;
		}

		private boolean isOutOfMaxRange(Double point) {
			return max < point;
		}
	}
}
