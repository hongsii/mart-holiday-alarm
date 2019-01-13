package com.hongsi.martholidayalarm.crawler.domain.holiday;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

public class MonthDayHolidayTest {

	@Test(expected = IllegalArgumentException.class)
	public void 날짜를_파싱할_수_없을때() {
		MonthDayHoliday.of("1월_2일", DateTimeFormatter.ofPattern("M/d"));
	}

	@Test
	public void 월과일만_있을때_연도_추가() {
		MonthDayHoliday monthDayHoliday = newInstance(Month.OCTOBER);
		YearMonth currentYearMonth = YearMonth.of(2018, Month.SEPTEMBER);

		assertThat(monthDayHoliday.getYearFromMonth(currentYearMonth))
				.isEqualTo(2018);
	}

	@Test
	public void 현재월이_11월_이상일때_휴일월이_더_클경우() {
		MonthDayHoliday monthDayHoliday = newInstance(Month.DECEMBER);
		YearMonth currentYearMonth = YearMonth.of(2018, Month.NOVEMBER);

		assertThat(monthDayHoliday.getYearFromMonth(currentYearMonth))
				.isEqualTo(2018);
	}

	@Test
	public void 현재월이_11월_이상일때_휴일월이_더_작은경우() {
		MonthDayHoliday monthDayHoliday = newInstance(Month.JANUARY);
		YearMonth currentYearMonth = YearMonth.of(2018, Month.NOVEMBER);

		assertThat(monthDayHoliday.getYearFromMonth(currentYearMonth))
				.isEqualTo(2019);
	}

	@Test
	public void Holiday로_변경() {
		MonthDayHoliday monthDayHoliday = newInstance(Month.MAY);

		assertThat(monthDayHoliday.toHoliday()).isEqualTo(Holiday.of(Year.now().getValue(), 5, 1));
	}

	private MonthDayHoliday newInstance(Month october) {
		MonthDay monthDay = MonthDay.of(october, 1);
		return MonthDayHoliday.of(monthDay);
	}
}