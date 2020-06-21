package com.hongsi.martholidayalarm.core.exception;

import com.hongsi.martholidayalarm.core.location.Location;

public class LocationOutOfRangeException extends RuntimeException {

    private static final String INVALID_MESSAGE_FORMAT = "%s는 %.0f과 %.0f 사이여야 합니다.";

    public LocationOutOfRangeException(Location.Range range) {
        super(getMessageWithTemplate(range));
    }

    public static String getMessageWithTemplate(Location.Range range) {
        return String.format(INVALID_MESSAGE_FORMAT, range.getName(), range.getMin(), range.getMax());
    }
}
