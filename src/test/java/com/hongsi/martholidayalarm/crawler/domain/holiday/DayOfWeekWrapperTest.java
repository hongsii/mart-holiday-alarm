package com.hongsi.martholidayalarm.crawler.domain.holiday;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DayOfWeekWrapperTest {

	@Test
	public void 문자로_파싱() {
		assertThat(DayOfWeekWrapper.of("월")).isEqualTo(DayOfWeekWrapper.MONDAY);
		assertThat(DayOfWeekWrapper.of("일요일")).isEqualTo(DayOfWeekWrapper.SUNDAY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 잘못된_문자_파싱() {
		DayOfWeekWrapper.of("월월");
	}
}