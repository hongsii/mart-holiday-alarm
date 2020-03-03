package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import org.junit.Test;

import java.time.Year;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class LotteMartDataTest {

	@Test
	public void 정상영업_제외() {
		LotteMartData lotteMartData = new LotteMartData();
		lotteMartData.setHolidayText("※2/5(화요일) 설날 당일 휴무!! 2월10일 일요일 정상영업, 2월24일 정기휴무");

		int currentYear = Year.now().getValue();
		Holiday holiday1 = Holiday.of(currentYear, 2, 5),
				holiday2 = Holiday.of(currentYear, 2, 10),
				holiday3 = Holiday.of(currentYear, 2, 24);

		List<Holiday> holidays = lotteMartData.excludeHoliday(
				asList(holiday1, holiday2, holiday3)
		);

		assertThat(holidays).hasSize(2).containsExactly(
				holiday1, holiday3
		);
	}
}