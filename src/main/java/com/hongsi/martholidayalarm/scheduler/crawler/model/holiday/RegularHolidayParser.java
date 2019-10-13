package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegularHolidayParser {

    static final int MAX_ADDITION_MONTH = 1;

    private List<YearMonth> yearMonths;

    private RegularHolidayParser(List<YearMonth> yearMonths) {
        this.yearMonths = yearMonths;
    }

    public static RegularHolidayParser from(LocalDate start) {
        List<YearMonth> yearMonths = new ArrayList<>(MAX_ADDITION_MONTH + 1);
        for (YearMonth current = YearMonth.from(start),
             end = current.plusMonths(MAX_ADDITION_MONTH);
             current.isBefore(end) || current.equals(end);
             current = current.plusMonths(1)) {
            yearMonths.add(current);
        }
        return new RegularHolidayParser(yearMonths);
    }

    public List<LocalDate> parse(RegularHoliday regularHoliday) {
        return yearMonths.stream()
                .map(regularHoliday::parse)
                .collect(Collectors.toList());
    }
}
