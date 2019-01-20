package com.hongsi.martholidayalarm.scheduler;

import static org.assertj.core.api.Assertions.assertThat;

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
public class MartCrawlerSchedulerTest {

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void 마트_크롤링_스케줄_실행확인() {
		LocalDateTime startTime = LocalDateTime.of(2018, 3, 21, 2, 0);
		Date nextScheduleTime = getNextScheduleTime("0 0 3 ? * MON", startTime);
		assertEqualNextScheduleTime(nextScheduleTime, "2018-03-26 03:00:00");
	}

	@Test
	public void 업데이트_스케줄_실행확인() {
		LocalDateTime startTime = LocalDateTime.of(2018, 8, 11, 2, 0);
		Date nextScheduleTime = getNextScheduleTime("0 30 3 ? * MON", startTime);
		assertEqualNextScheduleTime(nextScheduleTime, "2018-08-13 03:30:00");
	}

	private Date getNextScheduleTime(String cronExpression, LocalDateTime startTime) {
		CronTrigger cronTrigger = new CronTrigger(cronExpression);
		ZonedDateTime zonedStartTime = startTime.atZone(ZoneId.systemDefault());
		Date startScheduleTime = Date.from(zonedStartTime.toInstant());
		return cronTrigger.nextExecutionTime(new TriggerContext() {
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
	}

	private void assertEqualNextScheduleTime(Date nextScheduleTime, String expectTime) {
		assertThat(DATE_FORMAT.format(nextScheduleTime)).isEqualTo(expectTime);
	}
}