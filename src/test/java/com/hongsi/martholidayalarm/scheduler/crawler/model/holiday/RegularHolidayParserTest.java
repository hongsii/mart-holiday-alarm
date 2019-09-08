package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RegularHolidayParserTest {

	private RegularHolidayParser regularHolidayParser;

	@Before
	public void setUp() throws Exception {
		regularHolidayParser = new RegularHolidayParser(LocalDate.of(2018, 5, 16));
	}

	@Test
	public void 정기휴일의_현재월의_몇번째_요일_반환() {
		RegularHolidayParser regularHolidayParser = new RegularHolidayParser(
				LocalDate.of(2018, 5, 30));

		assertThat(regularHolidayParser
				.generateNthDayOfWeekInMonth(
						RegularHoliday.of(WeekWrapper.SECOND, DayOfWeekWrapper.WEDNESDAY)))
				.isEqualTo(LocalDate.of(2018, 5, 9));
		assertThat(regularHolidayParser
				.generateNthDayOfWeekInMonth(
						RegularHoliday.of(WeekWrapper.FOURTH, DayOfWeekWrapper.SUNDAY)))
				.isEqualTo(LocalDate.of(2018, 5, 27));
	}

	@Test
	public void 범위안이면_월_증가() {
		YearMonth current = regularHolidayParser.getCurrent();
		assertThat(regularHolidayParser.plusMonth()).isGreaterThan(current);
	}

	@Test
	public void 최대월인_경우_증가하지않음() {
		while (regularHolidayParser.canParse()) {
			regularHolidayParser.plusMonth();
		}

		YearMonth current = regularHolidayParser.getCurrent();
		assertThat(regularHolidayParser.plusMonth()).isEqualTo(current);
	}

	@Test
	public void 시작_날짜_범위_안인지_확인() {
		LocalDate before = regularHolidayParser
				.generateNthDayOfWeekInMonth(
						RegularHoliday.of(WeekWrapper.SECOND, DayOfWeekWrapper.WEDNESDAY));
		assertThat(regularHolidayParser.isInRange(before)).isFalse();
		LocalDate equal = regularHolidayParser
				.generateNthDayOfWeekInMonth(
						RegularHoliday.of(WeekWrapper.THIRD, DayOfWeekWrapper.WEDNESDAY));
		assertThat(regularHolidayParser.isInRange(equal)).isTrue();
		LocalDate after = regularHolidayParser
				.generateNthDayOfWeekInMonth(
						RegularHoliday.of(WeekWrapper.FOURTH, DayOfWeekWrapper.WEDNESDAY));
		assertThat(regularHolidayParser.isInRange(after)).isTrue();
	}
}