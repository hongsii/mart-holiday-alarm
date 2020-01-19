package com.hongsi.martholidayalarm.core.holiday

import spock.lang.Specification

import java.time.LocalDate

class HolidayTest extends Specification {

    def "Should create from date"() {
        given:
        def year = 2018
        def month = 1
        def dayOfMonth = 11

        expect:
        Holiday.of(year, month, dayOfMonth) == Holiday.of(LocalDate.of(year, month, dayOfMonth))
    }

    def "Should be upcoming when holiday is equal or after current date"() {
        given:
        def holiday = Holiday.of(date)

        when:
        def actual = holiday.isUpcoming()

        then:
        actual == expected

        where:
        date                         || expected
        LocalDate.now()              || true
        LocalDate.now().plusDays(1)  || true
        LocalDate.now().minusDays(1) || false
    }

    def "Format holiday to string"() {
        given:
        def holiday = Holiday.of(year, month, dayOfMonth)

        expect:
        holiday.getFormattedHoliday() == expected

        where:
        year | month | dayOfMonth || expected
        2019 | 1     | 1          || "2019-01-01 (화)"
        2019 | 10    | 12         || "2019-10-12 (토)"
    }

    def "Compare holiday"() {
        given:
        def holiday1 = Holiday.of(2019, 1, day1)
        def holiday2 = Holiday.of(2019, 1, day2)

        expect:
        holiday1 <  holiday2 == isBefore
        holiday1 == holiday2 == isEqual
        holiday1 >  holiday2 == isAfter

        where:
        day1 | day2 || isBefore | isEqual | isAfter
        1    | 1    || false    | true    | false
        1    | 2    || true     | false   | false
        2    | 1    || false    | false   | true
    }
}
