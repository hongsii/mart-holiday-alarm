package com.hongsi.martholidayalarm.crawler.model.holiday;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegularHolidayParser {

    private static final int ADDITION_MONTH_COUNT = 1;
    private static final int MAX_ADDITION_MONTH_COUNT = ADDITION_MONTH_COUNT + 1;
    private static final int MAX_DAYS = 31 * ADDITION_MONTH_COUNT;

    private final List<YearMonth> yearMonths;
    private final LocalDateRange range;

    private RegularHolidayParser(List<YearMonth> yearMonths, LocalDateRange range) {
        this.yearMonths = yearMonths;
        this.range = range;
    }

    public static RegularHolidayParser from(LocalDate date) {
        List<YearMonth> yearMonths = parseYearMonths(date);
        LocalDateRange range = LocalDateRange.withDays(date, MAX_DAYS);
        return new RegularHolidayParser(yearMonths, range);
    }

    private static List<YearMonth> parseYearMonths(LocalDate holiday) {
        List<YearMonth> yearMonths = new ArrayList<>(MAX_ADDITION_MONTH_COUNT);
        YearMonth current = YearMonth.from(holiday);
        YearMonth end = current.plusMonths(ADDITION_MONTH_COUNT);
        while (!current.isAfter(end)) {
            yearMonths.add(current);
            current = current.plusMonths(1);
        }
        return yearMonths;
    }

    public List<LocalDate> parse(RegularHoliday regularHoliday) {
        return yearMonths.stream()
                .map(regularHoliday::getDate)
                .filter(range::isBetween)
                .collect(Collectors.toList());
    }
}
