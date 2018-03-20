package com.hongsi.martholidayalarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MartHolidayAlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MartHolidayAlarmApplication.class, args);
	}
}
