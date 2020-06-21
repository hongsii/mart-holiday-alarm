package com.hongsi.martholidayalarm.client.location.converter.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.client.location.converter.LocationConversion;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoLocationSearchResult {

    @JsonProperty
    private List<KakaoLocationSearchItem> documents;

    public LocationConversion getLocationConversion() {
        return documents.stream()
                .findFirst()
                .map(item -> new LocationConversion(item.getAddress(), item.getLatitude(), item.getLongitude()))
                .orElseThrow(IllegalArgumentException::new);
    }
}
