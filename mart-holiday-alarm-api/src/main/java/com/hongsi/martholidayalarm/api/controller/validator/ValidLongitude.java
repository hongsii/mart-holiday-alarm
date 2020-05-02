package com.hongsi.martholidayalarm.api.controller.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLongitudeValidator.class)
public @interface ValidLongitude {

    String message() default "Invalid longitude";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

