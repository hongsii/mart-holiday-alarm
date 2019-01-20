package com.hongsi.martholidayalarm.api.mart;

import com.hongsi.martholidayalarm.exception.MissingParameterException;
import com.hongsi.martholidayalarm.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.hongsi.martholidayalarm.api.mart")
public class MartErrorAdvice {

	@ExceptionHandler({IllegalArgumentException.class, MissingParameterException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This api request has invalid parameters, check parameters")
	public ResponseEntity<String> handleBadRequest(Exception exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found resource of request")
	public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}
