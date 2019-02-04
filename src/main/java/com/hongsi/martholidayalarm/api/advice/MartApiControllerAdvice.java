package com.hongsi.martholidayalarm.api.advice;

import com.hongsi.martholidayalarm.api.controller.MartApiController;
import com.hongsi.martholidayalarm.api.response.ApiException;
import com.hongsi.martholidayalarm.api.response.ApiResponseCode;
import com.hongsi.martholidayalarm.exception.CannotParseMartTypeException;
import com.hongsi.martholidayalarm.exception.LocationOutOfRangeException;
import com.hongsi.martholidayalarm.exception.MissingParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MartApiController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MartApiControllerAdvice {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingParameterException.class)
	public ApiException handleLocationException(MissingParameterException e) {
		ApiException exception = ApiException.of(ApiResponseCode.BAD_REQUEST, e.getMessage());
		log.error("[API][{}] {}", exception.getCode(), exception.getMessage());
		return exception;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CannotParseMartTypeException.class)
	public ApiException handleInvalidMartType(CannotParseMartTypeException e) {
		ApiException exception = ApiException.withInfo(ApiResponseCode.BAD_REQUEST, e.getMessage(), e.getPossibleMartTypes());
		log.error("[API][{}] {}", exception.getCode(), exception.getMessage());
		return exception;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(LocationOutOfRangeException.class)
	public ApiException handleLocationException(LocationOutOfRangeException e) {
		ApiException exception = ApiException.of(ApiResponseCode.BAD_PARAMETER, e.getMessage());
		log.error("[API][{}] {}", exception.getCode(), exception.getMessage());
		return exception;
	}
}
