package com.hongsi.martholidayalarm.domain.mart;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.Test;

public class HolidayTest {

	@Test
	public void 연월일로_휴일_생성() {
		int year = 2018;
		int month = 1;
		int dayOfMonth = 11;
		Holiday holiday = Holiday.of(year, month, dayOfMonth);

		assertThat(holiday).isEqualTo(Holiday.of(LocalDate.of(year, month, dayOfMonth)));
	}

	@Test
	public void 같은_휴일_검증() {
		Holiday holiday1 = Holiday.of(2018, 1, 11);
		Holiday holiday = Holiday.of(2018, 1, 11);

		assertThat(holiday1).isEqualTo(holiday);
	}
}