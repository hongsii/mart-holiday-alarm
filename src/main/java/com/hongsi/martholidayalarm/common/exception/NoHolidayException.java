package com.hongsi.martholidayalarm.common.exception;

public class NoHolidayException extends Exception {

	@Override
	public String getMessage() {
		return "Holiday doesn't exist";
	}
}
