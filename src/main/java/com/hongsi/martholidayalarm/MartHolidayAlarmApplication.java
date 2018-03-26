package com.hongsi.martholidayalarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class MartHolidayAlarmApplication {

	public static final String APP_PROPERTIES = "spring.config.location=classpath:application.yml"
			+ "app/conf/MartHolidayAlarm/prod-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(MartHolidayAlarmApplication.class)
				.properties(APP_PROPERTIES)
				.run(args);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}
}
