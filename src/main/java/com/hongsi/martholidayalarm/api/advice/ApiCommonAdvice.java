package com.hongsi.martholidayalarm.api.advice;

import com.hongsi.martholidayalarm.api.response.ApiException;
import com.hongsi.martholidayalarm.api.response.ApiResponseCode;
import com.hongsi.martholidayalarm.api.response.ErrorMessages;
import com.hongsi.martholidayalarm.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class ApiCommonAdvice {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ApiException handleResourceNotFound(ResourceNotFoundException e) {
		ApiException exception = ApiException.of(ApiResponseCode.NOT_FOUND);
		log.error("[API][{}] {}", exception.getCode(), e.getMessage());
		return exception;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class})
	public ApiException handleBadRequest(IllegalArgumentException e) {
		e.printStackTrace();
		ApiException exception = ApiException.of(ApiResponseCode.BAD_PARAMETER, e.getMessage());
		log.error("[API][{}] {}", exception.getCode(), exception.getMessage());
		return exception;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ApiException handleNumberFormat(NumberFormatException e) {
		ApiException exception = ApiException.of(ApiResponseCode.BAD_REQUEST);
		log.error("[API][{}] {}", exception.getCode(), e.getMessage());
		return exception;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiException<ErrorMessages> handleBinding(BindException e) {
		ErrorMessages errorMessages = new ErrorMessages(e.getBindingResult());
		ApiException<ErrorMessages> exception = ApiException.withInfo(ApiResponseCode.BAD_PARAMETER, errorMessages);
		log.error("[API][{}] {}", exception.getCode(), errorMessages);
		return exception;
	}
}
