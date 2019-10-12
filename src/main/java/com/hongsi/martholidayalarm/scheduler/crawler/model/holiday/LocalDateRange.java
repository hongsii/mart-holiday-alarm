package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import java.time.LocalDate;

public class LocalDateRange {

    private LocalDate start;
    private LocalDate end;

    private LocalDateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
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

    public static LocalDateRange withDays(LocalDate start, int days) {
        return of(start, start.plusDays(days));
    }

    public boolean isBetween(LocalDate date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
