package com.hongsi.martholidayalarm.config;

import com.hongsi.martholidayalarm.api.interceptor.ApiLogInterceptor;
import com.hongsi.martholidayalarm.constants.ProfileType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Profile(ProfileType.NOT_TEST)
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiLogInterceptor apiLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLogInterceptor).addPathPatterns("/api/**");
    }
}