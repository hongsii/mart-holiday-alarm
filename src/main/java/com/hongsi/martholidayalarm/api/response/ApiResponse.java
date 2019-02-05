package com.hongsi.martholidayalarm.api.response;

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

	public static ApiResponse<String> exception(ApiResponseCode code) {
		return new ApiResponse<>(code, code.getMessage(), "");
	}

	public static ApiResponse<String> exception(ApiException exception) {
		return new ApiResponse<>(exception.getCode(), exception.getMessage(), "");
	}

	public static <T> ApiResponse<T> exception(ApiException exception, T data) {
		return new ApiResponse<>(exception.getCode(), exception.getMessage(), data);
	}
}
