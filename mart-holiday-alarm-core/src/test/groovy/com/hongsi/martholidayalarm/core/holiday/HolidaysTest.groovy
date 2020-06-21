package com.hongsi.martholidayalarm.core.holiday

import spock.lang.Specification

import java.time.LocalDate

class HolidaysTest extends Specification {

    def "다가오는 휴일만 조회할 수 있다"() {
        given:
        def yesterday = Holiday.of(LocalDate.now().minusDays(1))
        def today = Holiday.of(LocalDate.now())
        def tomorrow = Holiday.of(LocalDate.now().plusDays(1))

        when:
        def holidays = Holidays.of([yesterday, today, tomorrow])
        def actual = holidays.upcomingHolidays

        then:
        actual == [today, tomorrow]
    }
}
