package com.hongsi.martholidayalarm.crawler.model.holiday

import com.hongsi.martholidayalarm.crawler.model.holiday.SolarLunarConverter
import spock.lang.Specification

import java.time.LocalDate

class SolarLunarConverterTest extends Specification {

    def "convert lunar to solar by new year"() {
        when:
        def solar = SolarLunarConverter.convertLunarToSolar(lunar)

        then:
        solar == expected

        where:
        lunar                    || expected
        LocalDate.of(2018, 1, 1) || LocalDate.of(2018, 2, 16)
        LocalDate.of(2019, 1, 1) || LocalDate.of(2019, 2, 5)
        LocalDate.of(2020, 1, 1) || LocalDate.of(2020, 1, 25)
    }

    def "convert lunar to solar by chuseok"() {
        when:
        def solar = SolarLunarConverter.convertLunarToSolar(lunar)

        then:
        solar == expected

        where:
        lunar                     || expected
        LocalDate.of(2018, 8, 15) || LocalDate.of(2018, 9, 24)
        LocalDate.of(2019, 8, 15) || LocalDate.of(2019, 9, 13)
        LocalDate.of(2020, 8, 15) || LocalDate.of(2020, 10, 1)
    }
}
