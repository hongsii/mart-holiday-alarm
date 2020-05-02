package com.hongsi.martholidayalarm.api.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {

	private ApiResponseCode code;
	private String message;
	private T data;

	private ApiResponse(ApiResponseCode code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> ok(T data) {
		return ApiResponse.of(ApiResponseCode.OK, data);
	}

	public static <T> ApiResponse<T> of(ApiResponseCode code, T data) {
		return new ApiResponse<>(code, code.getMessage(), data);
	}
}
