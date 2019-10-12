package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday

import spock.lang.Specification

class KoreanWeekTest extends Specification {

    def "Should parse week from korean or numeric character"() {
        when:
        def actual = text.collect { KoreanWeek.of(it) }

        then:
        actual.every { it == expected }

        where:
        text            || expected
        ["첫", "1"]      || KoreanWeek.FIRST
        ["둘", "두", "2"] || KoreanWeek.SECOND
        ["셋", "3"]      || KoreanWeek.THIRD
        ["넷", "4"]      || KoreanWeek.FOURTH
        ["다섯", "5"]     || KoreanWeek.FIFTH
    }

    def "Should parse to list from korean or numeric character"() {
        when:
        def actual = KoreanWeek.parseToCollection(text)

        then:
        actual == expected

        where:
        text     || expected
        "첫"      || [KoreanWeek.FIRST]
        "첫, 둘"  || [KoreanWeek.FIRST, KoreanWeek.SECOND]
        "첫,둘,셋" || [KoreanWeek.FIRST, KoreanWeek.SECOND, KoreanWeek.THIRD]
    }

    def "Shouldn't parse week from invalid text"() {
        when:
        KoreanWeek.of("일")

        then:
        thrown(IllegalArgumentException)
    }
}
