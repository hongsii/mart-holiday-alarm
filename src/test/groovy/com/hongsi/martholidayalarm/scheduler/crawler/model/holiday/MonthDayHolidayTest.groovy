package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday

import com.hongsi.martholidayalarm.domain.mart.Holiday
import spock.lang.Specification

import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MonthDayHolidayTest extends Specification {

    def "Should parse from text with formatter"() {
        given:
        def formatter = DateTimeFormatter.ofPattern("M/d")

        when:
        def actual = MonthDayHoliday.parse(text, formatter)

        then:
        actual == new MonthDayHoliday(Month.SEPTEMBER, 29)

        where:
        text    | _
        "9/29"  | _
        "09/29" | _
    }

    def "Shouldn't parse from invalid text"() {
        given:
        def text = "9-29"
        def formatter = DateTimeFormatter.ofPattern("M/d")

        when:
        MonthDayHoliday.parse(text, formatter)

        then:
        thrown(IllegalArgumentException)
    }

    def "Should get current year if current month is before november"() {
        given:
        def monthDayHoliday = new MonthDayHoliday(holidayMonth, 1)
        def currentYearMonth = YearMonth.of(2018, currentMonth)

        when:
        def actual = monthDayHoliday.getYearFromMonth(currentYearMonth)

        then:
        actual == currentYearMonth.getYear()

        where:
        holidayMonth   | currentMonth    | _
        Month.OCTOBER  | Month.SEPTEMBER | _
        Month.NOVEMBER | Month.SEPTEMBER | _
    }

    def "Should get next year if current month is equal or after november and holiday is before february"() {
        given:
        def monthDayHoliday = new MonthDayHoliday(holidayMonth, 1)
        def currentYearMonth = YearMonth.of(2018, currentMonth)

        when:
        def actual = monthDayHoliday.getYearFromMonth(currentYearMonth)

        then:
        actual == currentYearMonth.getYear() + 1

        where:
        holidayMonth   | currentMonth   | _
        Month.JANUARY  | Month.NOVEMBER | _
        Month.JANUARY  | Month.DECEMBER | _
        Month.FEBRUARY | Month.NOVEMBER | _
        Month.FEBRUARY | Month.DECEMBER | _
    }

    def "get holiday domain"() {
        given:
        def monthDayHoliday = new MonthDayHoliday(Month.SEPTEMBER, 29)
        def currentYear = Year.now().getValue()

        expect:
        monthDayHoliday.toHoliday() == Holiday.of(currentYear, 9, 29)
    }
}
