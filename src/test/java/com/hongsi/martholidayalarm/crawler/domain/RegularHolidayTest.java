package com.hongsi.martholidayalarm.crawler.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegularHolidayTest {

	@Test
	public void 텍스트로_객체_생성_확인() {
		String regularHolidayText1 = "둘째주 일요일";
		String regularHolidayText2 = "넷 째주 수요일";

		RegularHoliday regularHoliday1 = new RegularHoliday(regularHolidayText1);
		RegularHoliday regularHoliday2 = new RegularHoliday(regularHolidayText2);

		assertEquals(2, regularHoliday1.getWeeks().get(0).intValue());
		assertEquals(DayOfWeek.SUNDAY, regularHoliday1.getDayOfWeeks().get(0));
		assertEquals(4, regularHoliday2.getWeeks().get(0).intValue());
		assertEquals(DayOfWeek.WEDNESDAY, regularHoliday2.getDayOfWeeks().get(0));
	}

	@Test
	public void 두개_이상의_휴일일_때_객체_생성_확인() {
		String regularHolidayText = "둘째,넷째주 월요일, 일요일";

		RegularHoliday regularHoliday = new RegularHoliday(regularHolidayText);

		assertEquals(2, regularHoliday.getWeeks().size());
		assertEquals(2, regularHoliday.getDayOfWeeks().size());
		assertEquals(2, regularHoliday.getWeeks().get(0).intValue());
		assertEquals(4, regularHoliday.getWeeks().get(1).intValue());
		assertEquals(DayOfWeek.MONDAY, regularHoliday.getDayOfWeeks().get(0));
		assertEquals(DayOfWeek.SUNDAY, regularHoliday.getDayOfWeeks().get(1));
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
		assertThat(holidays, is(expected));
	}
}