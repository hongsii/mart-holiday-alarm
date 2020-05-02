package com.hongsi.martholidayalarm.api.exception;

import com.hongsi.martholidayalarm.core.mart.MartType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InvalidMartTypeException extends RuntimeException {

    private static final Map<String, String> MART_TYPES = new HashMap<>(1);

    static {
        String joinedMartTypeNames = Arrays.stream(MartType.values())
                .map(MartType::name)
                .collect(Collectors.joining(", "));
        MART_TYPES.put("martTypes", joinedMartTypeNames);
    }

    private final String requestValue;

    public InvalidMartTypeException(String requestValue) {
        this.requestValue = requestValue;
    }

    @Override
    public String getMessage() {
        return String.format("잘못된 마트입니다. [value : %s]", requestValue);
    }

    public Map<String, String> getPossibleMartTypes() {
        return MART_TYPES;
    }
}
