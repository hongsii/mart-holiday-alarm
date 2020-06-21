package com.hongsi.martholidayalarm.api.dto.mart;

import com.hongsi.martholidayalarm.core.mart.MartType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MartTypeDto {

    private String value;
    private String displayName;

    public MartTypeDto(MartType martType) {
        value = martType.name().toLowerCase();
        displayName = martType.getName();
    }
}
