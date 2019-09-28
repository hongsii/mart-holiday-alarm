package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday

import spock.lang.Specification

class RegularHolidayTest extends Specification {

    def "Should be equal holiday with same week and day of week"() {
        given:
        def week = KoreanWeek.FIRST
        def dayOfWeek = KoreanDayOfWeek.SUNDAY

        expect:
        RegularHoliday.of(week, dayOfWeek) == RegularHoliday.of(week, dayOfWeek)
    }
}
