package com.hongsi.martholidayalarm;

import com.hongsi.martholidayalarm.common.constant.CommonConstants;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MartHolidayAlarmApplication {

	public static final String APP_PROPERTIES = "spring.config.location="
			+ "classpath:application.yml"
			+ ", " + CommonConstants.EXTERNAL_CONFIG_FILEPATH + "prod-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(MartHolidayAlarmApplication.class)
				.properties(APP_PROPERTIES)
				.run(args);
	}
}
