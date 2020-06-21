package com.hongsi.martholidayalarm.api.controller.advice;

import com.hongsi.martholidayalarm.api.controller.MartController;
import com.hongsi.martholidayalarm.api.dto.ApiException;
import com.hongsi.martholidayalarm.api.dto.ApiResponseCode;
import com.hongsi.martholidayalarm.api.exception.InvalidMartTypeException;
import com.hongsi.martholidayalarm.api.exception.ResourceNotFoundException;
import com.hongsi.martholidayalarm.core.exception.LocationOutOfRangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MartController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MartControllerAdvice {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiException<?> handleResourceNotFound(ResourceNotFoundException e) {
        ApiException<?> exception = ApiException.of(ApiResponseCode.NOT_FOUND);
        log.error("code: {}, message: {}", exception.getCode(), e.getMessage());
        return exception;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidMartTypeException.class)
    public ApiException<?> handleInvalidMartType(InvalidMartTypeException e) {
        ApiException<?> exception = ApiException.withInfo(ApiResponseCode.BAD_REQUEST, e.getMessage(), e.getPossibleMartTypes());
        log.error("code: {}, message: {}", exception.getCode(), exception.getMessage());
        return exception;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LocationOutOfRangeException.class)
    public ApiException<?> handleLocationException(LocationOutOfRangeException e) {
        ApiException<?> exception = ApiException.of(ApiResponseCode.BAD_PARAMETER, e.getMessage());
        log.error("code: {}, message: {}", exception.getCode(), exception.getMessage());
        return exception;
    }
}
