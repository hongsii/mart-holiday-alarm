package com.hongsi.martholidayalarm.api.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		this("Not found resource of request");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
