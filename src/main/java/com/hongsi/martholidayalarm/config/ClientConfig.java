package com.hongsi.martholidayalarm.config;

import com.hongsi.martholidayalarm.client.location.converter.KakaoLocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClientInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ClientConfig {

    private static final int THREE_SECONDS = 3 * 1000;

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

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(THREE_SECONDS);
        factory.setReadTimeout(THREE_SECONDS);
        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }
}
