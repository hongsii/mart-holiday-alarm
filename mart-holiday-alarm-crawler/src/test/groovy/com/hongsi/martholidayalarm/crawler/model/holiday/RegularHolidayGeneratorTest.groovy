package com.hongsi.martholidayalarm.crawler.model.holiday

import com.hongsi.martholidayalarm.core.holiday.Holiday
import com.hongsi.martholidayalarm.crawler.model.holiday.RegularHoliday
import com.hongsi.martholidayalarm.crawler.model.holiday.RegularHolidayGenerator
import spock.lang.Specification

import java.time.LocalDate

import static com.hongsi.martholidayalarm.crawler.model.holiday.KoreanDayOfWeek.*
import static com.hongsi.martholidayalarm.crawler.model.holiday.KoreanWeek.*

class RegularHolidayGeneratorTest extends Specification {

    def "Should parse from holiday text"() {
        when:
        def regularHolidayGenerator = RegularHolidayGenerator.from(holidayText)

        then:
        regularHolidayGenerator == RegularHolidayGenerator.of(expected)

        where:
        holidayText            || expected
        "둘째,넷째주 수요일, 일요일"      || [RegularHoliday.of(SECOND, WEDNESDAY), RegularHoliday.of(SECOND, SUNDAY), RegularHoliday.of(FOURTH, WEDNESDAY), RegularHoliday.of(FOURTH, SUNDAY)]
        "둘째,셋째주 월요일, 토요일"      || [RegularHoliday.of(SECOND, MONDAY), RegularHoliday.of(SECOND, SATURDAY), RegularHoliday.of(THIRD, MONDAY), RegularHoliday.of(THIRD, SATURDAY)]
        "첫째주 목요일"              || [RegularHoliday.of(FIRST, THURSDAY)]
        "(단, 매월 2,4번째 일요일 휴점)" || [RegularHoliday.of(SECOND, SUNDAY), RegularHoliday.of(FOURTH, SUNDAY)]
    }

    def "Shouldn't parse from invalid holiday text"() {
        when:
        RegularHolidayGenerator.from(holidayText)

        then:
        thrown(IllegalArgumentException)

        where:
        holidayText | _
        null        | _
        ""          | _
        " "         | _
        "여섯째주 월요일"  | _
        "첫째주 워요일"   | _
    }

    def "Shouldn't parse from invalid regular holidays"() {
        when:
        RegularHolidayGenerator.of(regularHolidays)

        then:
        thrown(IllegalArgumentException)

        where:
        regularHolidays | _
        null            | _
        []              | _
    }

    def "Should generate regular holiday date from holiday text"() {
        given:
        def regularHolidayGenerator = RegularHolidayGenerator.from(holidayText)

        when:
        def actual = regularHolidayGenerator.generate(now)

        then:
        actual == expected

        where:
        holidayText       | now                        || expected
        "둘째,넷째주 수요일, 일요일" | LocalDate.of(2019, 10, 3)  || [Holiday.of(2019, 10, 9),
                                                           Holiday.of(2019, 10, 13),
                                                           Holiday.of(2019, 10, 23),
                                                           Holiday.of(2019, 10, 27)]
        "둘째,넷째주 수요일, 일요일" | LocalDate.of(2019, 10, 20) || [Holiday.of(2019, 10, 23),
                                                           Holiday.of(2019, 10, 27),
                                                           Holiday.of(2019, 11, 10),
                                                           Holiday.of(2019, 11, 13)]
        "넷째주 토요일"         | LocalDate.of(2019, 10, 3)  || [Holiday.of(2019, 10, 26)]
    }
}
