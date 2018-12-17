package com.hongsi.martholidayalarm.crawler.domain.holiday;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class WeekWrapperTest {

	@Test
	public void 문자열에_해당하는_주_반환() {
		assertThat(asList(WeekWrapper.of("첫"), WeekWrapper.of("1")))
				.containsOnly(WeekWrapper.FIRST);
		assertThat(asList(WeekWrapper.of("둘"), WeekWrapper.of("2")))
				.containsOnly(WeekWrapper.SECOND);
		assertThat(asList(WeekWrapper.of("셋"), WeekWrapper.of("3")))
				.containsOnly(WeekWrapper.THIRD);
		assertThat(asList(WeekWrapper.of("넷"), WeekWrapper.of("4")))
				.containsOnly(WeekWrapper.FOURTH);
		assertThat(asList(WeekWrapper.of("다섯"), WeekWrapper.of("5")))
				.containsOnly(WeekWrapper.FIFTH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 일치하는_주가_없을때() {
		WeekWrapper.of("실패");
	}
}