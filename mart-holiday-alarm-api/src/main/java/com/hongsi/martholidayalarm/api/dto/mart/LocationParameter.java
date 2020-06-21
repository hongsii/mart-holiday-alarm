package com.hongsi.martholidayalarm.api.dto.mart;

import com.hongsi.martholidayalarm.api.controller.validator.ValidLatitude;
import com.hongsi.martholidayalarm.api.controller.validator.ValidLongitude;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class LocationParameter {

    private static final int DEFAULT_DISTANCE = 3;

    @ValidLatitude
    private Double latitude;

    @ValidLongitude
    private Double longitude;

    @Max(50)
    @Min(1)
    private Integer distance = DEFAULT_DISTANCE;
}
