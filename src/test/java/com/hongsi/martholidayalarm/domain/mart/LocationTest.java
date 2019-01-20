package com.hongsi.martholidayalarm.domain.mart;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.domain.mart.Location.Range;
import com.hongsi.martholidayalarm.exception.LocationOutOfRangeException;
import org.junit.Test;

public class LocationTest {

	private static final Double VALID_LATITUDE = Range.Latitude.getMax() / 2;
	private static final Double VALID_LONGITUDE = Range.Longitude.getMax() / 2;

	@Test(expected = IllegalArgumentException.class)
	public void 위도는_필수() {
		createLocation(null, VALID_LONGITUDE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 경도는_필수() {
		createLocation(VALID_LATITUDE, null);
	}

	@Test(expected = LocationOutOfRangeException.class)
	public void 위도_최소_범위_확인() {
		createLocation(Range.Latitude.getMin() - 1, VALID_LONGITUDE);
	}

	@Test(expected = LocationOutOfRangeException.class)
	public void 위도_최대_범위_확인() {
		createLocation(Range.Latitude.getMax() + 1, VALID_LONGITUDE);
	}

	@Test(expected = LocationOutOfRangeException.class)
	public void 경도_최소_범위_확인() {
		createLocation(VALID_LATITUDE, Range.Longitude.getMin() - 1);
	}

	@Test(expected = LocationOutOfRangeException.class)
	public void 경도_최대_범위_확인() {
		createLocation(VALID_LATITUDE, Range.Longitude.getMax() + 1);
	}

	@Test
	public void 동일한_좌표_객체_확인() {
		Double latitude = Double.valueOf(37.5402640), longitude = Double.valueOf(127.0533984);

		assertThat(createLocation(latitude, longitude)).isEqualTo(createLocation(latitude, longitude));
	}

	private Location createLocation(Double latitude, Double longitude) {
		return Location.of(latitude, longitude);
	}
}