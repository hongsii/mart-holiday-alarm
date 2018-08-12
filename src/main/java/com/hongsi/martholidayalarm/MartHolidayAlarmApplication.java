package com.hongsi.martholidayalarm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MartHolidayAlarmApplication {

	public static final String APP_PROPERTIES = "spring.config.location="
			+ "classpath:application.yml"
			+ ", /app/MartHolidayAlarm/conf/prod-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(MartHolidayAlarmApplication.class)
				.properties(APP_PROPERTIES)
				.run(args);
	}
}
