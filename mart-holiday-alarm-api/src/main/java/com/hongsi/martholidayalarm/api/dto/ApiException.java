package com.hongsi.martholidayalarm.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiException<T> {

    private final ApiResponseCode code;
    private final String message;
    @JsonInclude(Include.NON_NULL)
    private final T info;

    private ApiException(ApiResponseCode code, String message, T info) {
        this.code = code;
        this.message = message != null ? message : code.getMessage();
        this.info = info;
    }

    public static ApiException<Void> of(ApiResponseCode code) {
        return new ApiException<>(code, null, null);
    }

    public static ApiException<Void> of(ApiResponseCode code, String message) {
        return new ApiException<>(code, message, null);
    }

    public static <T> ApiException<T> withInfo(ApiResponseCode code, T info) {
        return new ApiException<>(code, null, info);
    }

    public static <T> ApiException<T> withInfo(ApiResponseCode code, String message, T info) {
        return new ApiException<>(code, message, info);
    }
}
