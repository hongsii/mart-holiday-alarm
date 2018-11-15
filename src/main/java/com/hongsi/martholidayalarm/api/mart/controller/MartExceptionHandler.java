package com.hongsi.martholidayalarm.api.mart.controller;

import com.hongsi.martholidayalarm.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice("com.hongsi.martholidayalarm.api.mart.controller")
public class MartExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This api request has invalid parameters, check parameters")
	public ResponseEntity<String> handleBadRequest(IllegalArgumentException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found resource of request")
	public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}
