package com.hongsi.martholidayalarm.crawler.domain

import com.hongsi.martholidayalarm.core.mart.MartType
import spock.lang.Specification
import spock.lang.Unroll

class InvalidCrawledMartTest extends Specification {

    def "Should be required martType and realId"() {
        given:
        def builder = InvalidCrawledMart.builder()
                .martType(martType)
                .realId(realId)

        when:
        builder.build()

        then:
        thrown(IllegalArgumentException.class)

        where:
        martType       | realId
        null           | null
        MartType.EMART | null
        null           | "1"
    }

    @Unroll
    def "Should check for invalid mart"() {
        given:
        def invalidMart = InvalidCrawledMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .enable(enable)
                .build()

        when:
        def invalid = invalidMart.isInvalid(checkMartType, checkRealId)

        then:
        invalid == expected

        where:
        enable | checkMartType      | checkRealId || expected
        true   | MartType.EMART     | "1"         || true
        true   | MartType.EMART     | "2"         || false
        true   | MartType.LOTTEMART | "1"         || false
        true   | MartType.LOTTEMART | "2"         || false

        false  | MartType.EMART     | "1"         || false
        false  | MartType.EMART     | "2"         || false
        false  | MartType.LOTTEMART | "1"         || false
        false  | MartType.LOTTEMART | "2"         || false
    }
}
