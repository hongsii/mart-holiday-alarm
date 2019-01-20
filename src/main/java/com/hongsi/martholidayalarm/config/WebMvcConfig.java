package com.hongsi.martholidayalarm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	private static final String[] RESOURCE_LOCATIONS = {
			"classpath:/static/"
	};

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/**")
				.addResourceLocations(RESOURCE_LOCATIONS)
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}
}
