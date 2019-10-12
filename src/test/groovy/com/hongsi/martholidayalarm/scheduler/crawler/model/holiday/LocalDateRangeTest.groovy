package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday


import spock.lang.Specification

import java.time.LocalDate

class LocalDateRangeTest extends Specification {

    def "Should be before end date"() {
        given:

        when:
        LocalDateRange.of(startDate, endDate)

        then:
        thrown(IllegalArgumentException)

        where:
        startDate                 | endDate
        LocalDate.of(2019, 10, 9) | null
        null                      | LocalDate.of(2019, 10, 9)
        null                      | null
        LocalDate.of(2019, 10, 9) | startDate.minusDays(1)

    }

    def "Should be between range"() {
        given:
        def localDateRange = LocalDateRange.of(start, end)

        when:
        def actual = localDateRange.isBetween(date)

        then:
        actual == expected

        where:
        start                     | end                       | date               || expected
        LocalDate.of(2019, 10, 1) | LocalDate.of(2019, 10, 9) | start              || true
        LocalDate.of(2019, 10, 1) | LocalDate.of(2019, 10, 9) | start.plusDays(1)  || true
        LocalDate.of(2019, 10, 1) | LocalDate.of(2019, 10, 9) | end                || true
        LocalDate.of(2019, 10, 1) | LocalDate.of(2019, 10, 9) | end.minusDays(1)   || true

        LocalDate.of(2019, 10, 1) | LocalDate.of(2019, 10, 9) | start.minusDays(1) || false
        LocalDate.of(2019, 10, 1) | LocalDate.of(2019, 10, 9) | end.plusDays(1)    || false
    }

    def "Should be between range with days"() {
        given:
        def localDateRange = LocalDateRange.withDays(start, days)

        when:
        def actual = localDateRange.isBetween(date)

        then:
        actual == expected

        where:
        start                     | days | date                     || expected
        LocalDate.of(2019, 10, 1) | 8    | start                    || true
        LocalDate.of(2019, 10, 1) | 8    | start.plusDays(1)        || true
        LocalDate.of(2019, 10, 1) | 8    | start.plusDays(days)     || true
        LocalDate.of(2019, 10, 1) | 8    | start.plusDays(days - 1) || true

        LocalDate.of(2019, 10, 1) | 8    | start.minusDays(1)       || false
        LocalDate.of(2019, 10, 1) | 8    | start.plusDays(days + 1) || false
    }
}
