package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday

import spock.lang.Specification

import java.time.LocalDate

import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanDayOfWeek.SUNDAY
import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanDayOfWeek.WEDNESDAY
import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanWeek.FOURTH
import static com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.KoreanWeek.SECOND

class RegularHolidayParserTest extends Specification {

    def "Should parse date of regular holiday"() {
        given:
        def regularHolidayParser = RegularHolidayParser.from(now)
        def regularHoliday = RegularHoliday.of(koreanWeek, koreanDayOfWeek)

        when:
        def actual = regularHolidayParser.parse(regularHoliday)

        then:
        actual == expected

        where:
        now                       | koreanWeek | koreanDayOfWeek || expected
        LocalDate.of(2019, 10, 3) | SECOND     | WEDNESDAY       || [LocalDate.of(2019, 10, 9),
                                                                     LocalDate.of(2019, 11, 13)]
        LocalDate.of(2019, 10, 3) | FOURTH     | SUNDAY          || [LocalDate.of(2019, 10, 27),
                                                                     LocalDate.of(2019, 11, 24)]
    }
}
