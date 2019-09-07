package com.hongsi.martholidayalarm.config;

import com.hongsi.martholidayalarm.client.location.converter.KakaoLocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClientInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "kakao.client")
    public LocationConvertClientInfo locationConverterClientInfo() {
        return new LocationConvertClientInfo();
    }

    @Bean
    public LocationConvertClient locationConvertClient(RestTemplateBuilder restTemplateBuilder,
                                                       LocationConvertClientInfo locationConvertClientInfo) {
        return new KakaoLocationConvertClient(
                restTemplateBuilder
                        .setConnectTimeout(Duration.ofSeconds(3))
                        .setReadTimeout(Duration.ofSeconds(3)),
                locationConvertClientInfo
        );
    }
}
