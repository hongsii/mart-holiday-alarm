package com.hongsi.martholidayalarm.client.location.converter.kakao;

import com.hongsi.martholidayalarm.client.location.converter.LocationConversion;
import com.hongsi.martholidayalarm.client.location.converter.LocationConverter;
import com.hongsi.martholidayalarm.client.location.converter.LocationConverterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
public class KakaoLocationConverter implements LocationConverter {

    private final LocationConverterProperties properties;
    private final RestTemplate restTemplate;
    private final HttpEntity<?> requestHttpEntity;

    public KakaoLocationConverter(RestTemplateBuilder restTemplateBuilder,
                                  LocationConverterProperties kakaoProperties) {
        this.properties = kakaoProperties;
        this.restTemplate = restTemplateBuilder.build();
        this.requestHttpEntity = createHttpEntityByProperties();
    }

    private HttpEntity<?> createHttpEntityByProperties() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + properties.getClientId());
        return new HttpEntity<>(httpHeaders);
    }

    @Override
    public LocationConversion convert(Supplier<String> querySupplier, Supplier<String> fallbackSupplier) {
        String query = querySupplier.get();
        try {
            return requestToApi(properties.getSearchUrl(), query);
        } catch (Exception e) {
            log.warn("failed to location convert. query: {}, message: {}", query, e.getMessage());
            return requestByFallback(fallbackSupplier.get());
        }
    }

    private LocationConversion requestToApi(String requestUrl, String query) {
        KakaoLocationSearchResult kakaoLocationSearchResult = restTemplate
                .exchange(
                        UriComponentsBuilder.fromHttpUrl(requestUrl)
                                .queryParam("query", query)
                                .queryParam("page", 1)
                                .queryParam("size", 5)
                                .build().toUriString(), HttpMethod.GET, requestHttpEntity, KakaoLocationSearchResult.class
                )
                .getBody();
        return Objects.requireNonNull(kakaoLocationSearchResult).getLocationConversion();
    }

    private LocationConversion requestByFallback(String query) {
        try {
            return requestToApi(properties.getAddressUrl(), query);
        } catch (Exception e) {
            log.error("failed to location fallback. query: {}, message: {}", query, e.getMessage());
            return LocationConversion.EMPTY;
        }
    }
}
