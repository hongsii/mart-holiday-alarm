package com.hongsi.martholidayalarm.common.mart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.Test;

public class HolidayTest {

	@Test
	public void getFormattedHolidayWithDayOfWeek() {
		Holiday holiday = Holiday.builder()
				.date(LocalDate.of(2018, 6, 14))
				.build();

		assertThat(holiday.getFormattedHolidayWithDayOfWeek()).isEqualTo("2018-6-14 (목)");

		holiday = Holiday.builder()
				.date(LocalDate.of(2018, 6, 1))
				.build();

		assertThat(holiday.getFormattedHolidayWithDayOfWeek()).isEqualTo("2018-6-1 (금)");
	}
}