package com.hongsi.martholidayalarm.scheduler;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

@RunWith(JUnit4.class)
public class PushSchedulerTest {

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void checkNotificationScheduleTime() {
		LocalDateTime startTime = LocalDateTime.of(2018, 9, 25, 10, 0);
		Date nextScheduleTime = getNextScheduleTime("0 0 9 ? * *", startTime);
		assertEqualNextScheduleTime(nextScheduleTime, "2018-09-26 09:00:00");
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