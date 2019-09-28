package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class KoreanWeekTest {

	@Test
	public void 문자열에_해당하는_주_반환() {
		Assertions.assertThat(asList(KoreanWeek.of("첫"), KoreanWeek.of("1")))
				.containsOnly(KoreanWeek.FIRST);
		assertThat(asList(KoreanWeek.of("둘"), KoreanWeek.of("2")))
				.containsOnly(KoreanWeek.SECOND);
		assertThat(asList(KoreanWeek.of("셋"), KoreanWeek.of("3")))
				.containsOnly(KoreanWeek.THIRD);
		assertThat(asList(KoreanWeek.of("넷"), KoreanWeek.of("4")))
				.containsOnly(KoreanWeek.FOURTH);
		assertThat(asList(KoreanWeek.of("다섯"), KoreanWeek.of("5")))
				.containsOnly(KoreanWeek.FIFTH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 일치하는_주가_없을때() {
		KoreanWeek.of("실패");
	}
}