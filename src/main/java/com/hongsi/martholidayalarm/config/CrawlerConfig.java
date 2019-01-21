package com.hongsi.martholidayalarm.config;

import com.hongsi.martholidayalarm.client.location.converter.LocationConverterClientInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
@EnableScheduling
public class CrawlerConfig {

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	@Bean
	@ConfigurationProperties(prefix = "kakao.client")
	public LocationConverterClientInfo kakaoClientInfo() {
		return new LocationConverterClientInfo();
	}
}
