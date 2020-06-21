package com.hongsi.martholidayalarm.crawler.model.holiday

import spock.lang.Specification

import static com.hongsi.martholidayalarm.crawler.model.holiday.KoreanDayOfWeek.*

class KoreanDayOfWeekTest extends Specification {

    def "Should parse day of week from single korean character"() {
        expect:
        of(text) == expected

        where:
        text || expected
        "월"  || MONDAY
        "화"  || TUESDAY
        "수"  || WEDNESDAY
        "목"  || THURSDAY
        "금"  || FRIDAY
        "토"  || SATURDAY
        "일"  || SUNDAY
    }

    def "Should parse day of week from full korean character"() {
        expect:
        of(text) == expected

        where:
        text  || expected
        "월요일" || MONDAY
        "화요일" || TUESDAY
        "수요일" || WEDNESDAY
        "목요일" || THURSDAY
        "금요일" || FRIDAY
        "토요일" || SATURDAY
        "일요일" || SUNDAY
    }

    def "Shouldn't parse day of week from invalid text"() {
        when:
        of(text)

        then:
        thrown(IllegalArgumentException)

        where:
        text  | _
        "잘"   | _
        "월월"  | _
        "왈요일" | _
    }

    def "Should parse to list from text"() {
        expect:
        parseToCollection(text) == expected

        where:
        text          || expected
        "월요일"         || [MONDAY]
        "월요일수요일"      || [MONDAY, WEDNESDAY]
        "월요일,수요일"     || [MONDAY, WEDNESDAY]
        "월요일, 수요일"    || [MONDAY, WEDNESDAY]
        "월요일,수요일,금요일" || [MONDAY, WEDNESDAY, FRIDAY]
    }
}
