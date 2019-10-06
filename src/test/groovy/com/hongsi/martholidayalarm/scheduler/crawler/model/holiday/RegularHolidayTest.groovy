package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday

import spock.lang.Specification

import java.time.LocalDate
import java.time.YearMonth

import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanDayOfWeek.*
import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanWeek.*

class RegularHolidayTest extends Specification {

    def "Should be equal holiday with same week and day of week"() {
        given:
        def week = FIRST
        def dayOfWeek = SUNDAY

        expect:
        RegularHoliday.of(week, dayOfWeek) == RegularHoliday.of(week, dayOfWeek)
    }

    def "Shouldn't be created, if all parameter don't exist"() {
        when:
        RegularHoliday.of(koreanWeek, koreanDayOfWeek)

        then:
        thrown(IllegalArgumentException)

        where:
        koreanWeek | koreanDayOfWeek | _
        null       | null            | _
        FIRST      | null            | _
        null       | MONDAY          | _
    }

    def "parse Nth day of week in month"() {
        given:
        def regularHoliday = RegularHoliday.of(week, dayOfWeek)

        when:
        def actual = regularHoliday.parse(yearMonth)

        then:
        actual == expected

        where:
        week   | dayOfWeek | yearMonth              || expected
        FIRST  | SUNDAY    | YearMonth.of(2019, 10) || LocalDate.of(2019, 10, 6)
        SECOND | WEDNESDAY | YearMonth.of(2019, 10) || LocalDate.of(2019, 10, 9)
        THIRD  | MONDAY    | YearMonth.of(2019, 10) || LocalDate.of(2019, 10, 21)
        FOURTH | TUESDAY   | YearMonth.of(2019, 10) || LocalDate.of(2019, 10, 22)
    }
}
