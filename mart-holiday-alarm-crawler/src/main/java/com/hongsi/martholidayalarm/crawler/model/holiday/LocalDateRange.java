package com.hongsi.martholidayalarm.crawler.model.holiday;

import java.time.LocalDate;

public class LocalDateRange {

    private final LocalDate start;
    private final LocalDate end;

    private LocalDateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public static LocalDateRange withDays(LocalDate start, int days) {
        return of(start, start.plusDays(days));
    }

    public static LocalDateRange of(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Must need both start and end");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Start must be before end");
        }
        return new LocalDateRange(start, end);
    }

    public boolean isBetween(LocalDate date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
