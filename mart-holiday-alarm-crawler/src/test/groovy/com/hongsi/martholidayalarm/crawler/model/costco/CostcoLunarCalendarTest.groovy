package com.hongsi.martholidayalarm.crawler.model.costco

import spock.lang.Specification

import java.time.LocalDate

class CostcoLunarCalendarTest extends Specification {

    def "convert lunar to solar"() {
        expect:
        CostcoParser.CostcoLunarCalendar.convertToSolar(lunar) == solar

        where:
        lunar                     || solar
        LocalDate.of(2025, 1, 1)  || LocalDate.of(2025, 1, 29)
        LocalDate.of(2025, 8, 15) || LocalDate.of(2025, 10, 6)
    }
}
