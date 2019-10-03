package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday

import spock.lang.Specification

import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanDayOfWeek.MONDAY
import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanDayOfWeek.SUNDAY
import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanWeek.FIRST

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
}
