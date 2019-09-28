package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KoreanDayOfWeekTest {

	@Test
	public void 문자로_파싱() {
		Assertions.assertThat(KoreanDayOfWeek.of("월")).isEqualTo(KoreanDayOfWeek.MONDAY);
		assertThat(KoreanDayOfWeek.of("일요일")).isEqualTo(KoreanDayOfWeek.SUNDAY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 잘못된_문자_파싱() {
		KoreanDayOfWeek.of("월월");
	}
}