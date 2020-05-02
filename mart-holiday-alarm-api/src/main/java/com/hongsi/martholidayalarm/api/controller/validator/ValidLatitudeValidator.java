package com.hongsi.martholidayalarm.api.controller.validator;

import com.hongsi.martholidayalarm.core.location.Location;

public class ValidLatitudeValidator extends ValidLocationRangeValidator<ValidLatitude> {

    public ValidLatitudeValidator() {
        super(Location.Range.Latitude);
    }
}
