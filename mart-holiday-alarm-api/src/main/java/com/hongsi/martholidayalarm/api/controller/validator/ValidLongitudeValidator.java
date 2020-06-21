package com.hongsi.martholidayalarm.api.controller.validator;

import com.hongsi.martholidayalarm.core.location.Location;

public class ValidLongitudeValidator extends ValidLocationRangeValidator<ValidLongitude> {

    public ValidLongitudeValidator() {
        super(Location.Range.Longitude);
    }
}
