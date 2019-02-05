package com.hongsi.martholidayalarm.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiResponseCode {

	OK("요청이 성공했습니다."),
	BAD_PARAMETER("잘못된 요청파라미터입니다."),
	BAD_REQUEST("잘못된 요청입니다."),
	NOT_FOUND("요청한 리소스를 찾을 수 없습니다."),
	SERVER_ERROR("서버 에러가 발생 중입니다.");

	private final String message;
}
