package com.hongsi.martholidayalarm.api.controller.advice;

import com.hongsi.martholidayalarm.api.dto.ApiException;
import com.hongsi.martholidayalarm.api.dto.ApiResponseCode;
import com.hongsi.martholidayalarm.api.dto.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class CommonControllerAdvice {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class})
	public ApiException<?> handleBadRequest(IllegalArgumentException e) {
		ApiException<?> exception = ApiException.of(ApiResponseCode.BAD_PARAMETER, e.getMessage());
		log.error("code: {}, message: {}", exception.getCode(), e.getMessage());
		return exception;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ApiException<?> handleNumberFormat(NumberFormatException e) {
		ApiException<?> exception = ApiException.of(ApiResponseCode.BAD_REQUEST);
		log.error("code: {}, message: {}", exception.getCode(), e.getMessage());
		return exception;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiException<ErrorMessages> handleBinding(BindException e) {
		ErrorMessages errorMessages = new ErrorMessages(e.getBindingResult());
		ApiException<ErrorMessages> exception = ApiException.withInfo(ApiResponseCode.BAD_PARAMETER, errorMessages);
		log.error("code: {}, message: {}", exception.getCode(), e.getMessage());
		return exception;
	}
}
