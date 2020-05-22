package com.hongsi.martholidayalarm.crawler.model.holiday;

import com.hongsi.martholidayalarm.core.holiday.Holiday;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RegularHolidayGenerator {

    private final List<RegularHoliday> regularHolidays;

    private RegularHolidayGenerator(List<RegularHoliday> regularHolidays) {
        this.regularHolidays = regularHolidays;
    }

    public static RegularHolidayGenerator from(String holidayText) {
        if (!StringUtils.hasText(holidayText)) {
            throw new IllegalArgumentException("Not exists holiday text");
        }
        List<KoreanWeek> weeks = KoreanWeek.parseToCollection(holidayText);
        List<KoreanDayOfWeek> dayOfWeeks = KoreanDayOfWeek.parseToCollection(holidayText);
        return of(createRegularHolidays(weeks, dayOfWeeks));
    }

    private static List<RegularHoliday> createRegularHolidays(List<KoreanWeek> weeks, List<KoreanDayOfWeek> dayOfWeeks) {
        return weeks.stream()
                .flatMap(week -> dayOfWeeks.stream()
                        .map(dayOfWeek -> RegularHoliday.of(week, dayOfWeek))
                )
                .collect(Collectors.toList());
    }

    public static RegularHolidayGenerator of(List<RegularHoliday> regularHolidays) {
        if (regularHolidays == null || regularHolidays.isEmpty()) {
            throw new IllegalArgumentException("Not found regular holiday");
        }
        return new RegularHolidayGenerator(regularHolidays);
    }

    public List<Holiday> generate(LocalDate startDate) {
        RegularHolidayParser regularHolidayParser = RegularHolidayParser.from(startDate);
        return regularHolidays.stream()
                .map(regularHolidayParser::parse)
                .flatMap(List::stream)
                .map(Holiday::of)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegularHolidayGenerator that = (RegularHolidayGenerator) o;
        return Objects.equals(regularHolidays, that.regularHolidays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regularHolidays);
    }
}
