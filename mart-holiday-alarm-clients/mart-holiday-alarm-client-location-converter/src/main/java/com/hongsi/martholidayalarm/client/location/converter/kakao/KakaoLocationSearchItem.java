package com.hongsi.martholidayalarm.client.location.converter.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class KakaoLocationSearchItem {

    @JsonProperty("road_address_name")
    private String roadAddress;
    @JsonProperty("address_name")
    private String address;
    @JsonProperty("y")
    private Double latitude;
    @JsonProperty("x")
    private Double longitude;

    public String getAddress() {
        return StringUtils.hasText(roadAddress) ? roadAddress : address;
    }
}
