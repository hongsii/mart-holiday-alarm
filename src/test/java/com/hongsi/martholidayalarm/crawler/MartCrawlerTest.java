package com.hongsi.martholidayalarm.crawler;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MartCrawlerTest {

	@Test
	public void 월요일마다_스케줄_실행확인() {
		CronTrigger cronTrigger = new CronTrigger("0 0 3 ? * MON");

		LocalDateTime startTime = LocalDateTime.of(2018, 3, 21, 02, 00);
		ZonedDateTime zonedStartTime = startTime.atZone(ZoneId.systemDefault());
		Date startScheduleTime = Date.from(zonedStartTime.toInstant());
		Date nextScheduleTime = cronTrigger.nextExecutionTime(new TriggerContext() {
			@Override
			public Date lastScheduledExecutionTime() {
				return startScheduleTime;
			}

			@Override
			public Date lastActualExecutionTime() {
				return startScheduleTime;
			}

			@Override
			public Date lastCompletionTime() {
				return startScheduleTime;
			}
		});
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		assertEquals("2018-03-26 03:00:00", format.format(nextScheduleTime));
	}
}