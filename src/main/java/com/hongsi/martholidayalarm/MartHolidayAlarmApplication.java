package com.hongsi.martholidayalarm;

import javax.servlet.Filter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class MartHolidayAlarmApplication {

	public static final String APP_PROPERTIES = "spring.config.location=classpath:application.yml,"
			+ "/app/MartHolidayAlarm/conf/prod-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(MartHolidayAlarmApplication.class)
				.properties(APP_PROPERTIES)
				.run(args);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
}
