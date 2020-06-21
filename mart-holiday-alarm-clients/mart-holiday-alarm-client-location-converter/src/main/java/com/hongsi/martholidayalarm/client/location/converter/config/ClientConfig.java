package com.hongsi.martholidayalarm.client.location.converter.config;

import com.hongsi.martholidayalarm.client.location.converter.LocationConverter;
import com.hongsi.martholidayalarm.client.location.converter.LocationConverterProperties;
import com.hongsi.martholidayalarm.client.location.converter.kakao.KakaoLocationConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ClientConfig {

    private static final int DEFAULT_TIMEOUT_SECONDS = 3 * 1000;

    @Bean
    @ConfigurationProperties(prefix = "kakao.client")
    public LocationConverterProperties kakaoProperties() {
        return new LocationConverterProperties();
    }

    @Bean
    public LocationConverter locationConverter(RestTemplateBuilder restTemplateBuilder,
                                               LocationConverterProperties LocationConverterProperties) {
        return new KakaoLocationConverter(
                restTemplateBuilder
                        .setConnectTimeout(Duration.ofMillis(DEFAULT_TIMEOUT_SECONDS))
                        .setReadTimeout(Duration.ofMillis(DEFAULT_TIMEOUT_SECONDS)),
                LocationConverterProperties
        );
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
