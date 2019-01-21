package com.hongsi.martholidayalarm.exception;

public class CannotConvertLocation extends RuntimeException {

	public CannotConvertLocation() {
		super("마트 주소를 변환할 수 없습니다.");
	}

	public CannotConvertLocation(String message) {
		super(message);
	}
}
