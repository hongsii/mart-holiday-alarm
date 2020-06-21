package com.hongsi.martholidayalarm.api.controller.validator;

import com.hongsi.martholidayalarm.core.exception.LocationOutOfRangeException;
import com.hongsi.martholidayalarm.core.location.Location;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public abstract class ValidLocationRangeValidator<A extends Annotation> implements ConstraintValidator<A, Double> {

    private final Location.Range range;
    private final String message;

    public ValidLocationRangeValidator(Location.Range range) {
        this.range = range;
        this.message = LocationOutOfRangeException.getMessageWithTemplate(range);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        boolean valid = value != null && range.isValid(value);
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
        return valid;
    }
}
