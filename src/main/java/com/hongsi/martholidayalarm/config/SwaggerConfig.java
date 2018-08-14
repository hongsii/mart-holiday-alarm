package com.hongsi.martholidayalarm.config;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.build()
				.useDefaultResponseMessages(false)
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("마트휴일알리미 API Documentation")
				.description("카카오톡 플러스친구 및 마트 정보를 제공합니다.\n\n"
						+ "마트휴일알리미 카카오톡봇 : https://pf.kakao.com/_BBAiC\n"
						+ "마트휴일알리미 Github : https://github.com/hongsii/mart-holiday-alarm")
				.contact(new Contact("hongsii", "https://github.com/hongsii", "pozt1234@gmail.com"))
				.version("v1")
				.build();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs")
				.setKeepQueryParams(true);
		registry.addRedirectViewController("/api/swagger-resources/configuration/ui",
				"/swagger-resources/configuration/ui");
		registry.addRedirectViewController("/api/swagger-resources/configuration/security",
				"/swagger-resources/configuration/security");
		registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
		registry.addRedirectViewController("/api", "/api/swagger-ui.html");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/api/**")
				.addResourceLocations("classpath:/META-INF/resources/");
	}
}
