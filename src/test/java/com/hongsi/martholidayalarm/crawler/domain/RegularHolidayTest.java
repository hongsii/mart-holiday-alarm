package com.hongsi.martholidayalarm.crawler.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class RegularHolidayTest {

	@Test
	public void 텍스트로_객체_생성_확인() {
		String regularHolidayText1 = "둘째주 일요일";
		String regularHolidayText2 = "넷 째주 수요일";

		RegularHoliday regularHoliday1 = new RegularHoliday(regularHolidayText1);
		RegularHoliday regularHoliday2 = new RegularHoliday(regularHolidayText2);

		assertThat(regularHoliday1.getWeeks()).first().isEqualTo(2);
		assertThat(regularHoliday1.getDayOfWeeks()).first().isEqualTo(DayOfWeek.SUNDAY);

		assertThat(regularHoliday2.getWeeks()).first().isEqualTo(4);
		assertThat(regularHoliday2.getDayOfWeeks()).first().isEqualTo(DayOfWeek.WEDNESDAY);
	}

	@Test
	public void 두개_이상의_휴일일_때_객체_생성_확인() {
		String regularHolidayText = "둘째,넷째주 월요일, 일요일";

		RegularHoliday regularHoliday = new RegularHoliday(regularHolidayText);

		assertThat(regularHoliday.getWeeks()).hasSize(2);
		assertThat(regularHoliday.getWeeks()).element(0).isEqualTo(2);
		assertThat(regularHoliday.getWeeks()).element(1).isEqualTo(4);
		assertThat(regularHoliday.getDayOfWeeks()).hasSize(2);
		assertThat(regularHoliday.getDayOfWeeks()).element(0).isEqualTo(DayOfWeek.MONDAY);
		assertThat(regularHoliday.getDayOfWeeks()).element(1).isEqualTo(DayOfWeek.SUNDAY);
	}

	@Test
	public void 두개_이상의_휴일일_때_객체_생성_확인2() {
		String regularHolidayText = "(단, 매월 2,4번째 일요일 휴점)";

		RegularHoliday regularHoliday = new RegularHoliday(regularHolidayText);

		assertThat(regularHoliday.getWeeks()).hasSize(2);
		assertThat(regularHoliday.getWeeks()).element(0).isEqualTo(2);
		assertThat(regularHoliday.getWeeks()).element(1).isEqualTo(4);
		assertThat(regularHoliday.getDayOfWeeks()).hasSize(1);
		assertThat(regularHoliday.getDayOfWeeks()).first().isEqualTo(DayOfWeek.SUNDAY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 잘못된_파라미터로_객체_생성시_예외() {
		String wrongText = "여섯째주숨요일";

		RegularHoliday regularHoliday = new RegularHoliday(wrongText);
	}

	@Test
	public void 휴일_생성() {
		RegularHoliday regularHoliday = new RegularHoliday("둘째,넷째주 수요일, 일요일");
		LocalDate startDate = LocalDate.of(2018, 5, 20);

		List<Holiday> holidays = regularHoliday.makeRegularHolidays(startDate);

		List<Holiday> expected = new ArrayList<>();
		expected.add(Holiday.builder()
				.date(LocalDate.of(2018, 5, 23))
				.build());
		expected.add(Holiday.builder()
				.date(LocalDate.of(2018, 5, 27))
				.build());
		expected.add(Holiday.builder()
				.date(LocalDate.of(2018, 6, 10))
				.build());
		expected.add(Holiday.builder()
				.date(LocalDate.of(2018, 6, 13))
				.build());
		expected.add(Holiday.builder()
				.date(LocalDate.of(2018, 6, 24))
				.build());
		expected.add(Holiday.builder()
				.date(LocalDate.of(2018, 6, 27))
				.build());
		assertThat(holidays).containsOnlyElementsOf(expected);
	}
}