package com.hongsi.martholidayalarm.mart.dto;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import java.time.LocalDate;
import org.junit.Test;

public class HolidaysTest {

	@Test
	public void 지나간_휴일_제외() {
		Holiday yesterday = Holiday.of(LocalDate.now().minusDays(1));
		Holiday now = Holiday.of(LocalDate.now());
		Holiday tomorrow = Holiday.of(LocalDate.now().plusDays(1));
		Holidays holidays = Holidays.of(asList(yesterday, now, tomorrow));

		assertThat(holidays.getUpcomingHolidays())
				.hasSize(2)
				.containsExactly(now, tomorrow);
	}
}