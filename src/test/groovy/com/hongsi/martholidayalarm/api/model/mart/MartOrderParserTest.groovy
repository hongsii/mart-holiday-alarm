package com.hongsi.martholidayalarm.api.model.mart

import com.hongsi.martholidayalarm.api.model.mart.MartOrderParser
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.data.domain.Sort.Order

class MartOrderParserTest extends Specification {

    @Unroll("#order => #expected")
    def "Should parse order from valid format"() {
        when:
        def actual = MartOrderParser.parse(order)

        then:
        actual == Optional.of(expected)

        where:
        order           || expected
        "martType"      || Order.asc("martType")
        "martType:"     || Order.asc("martType")
        "martType:asc"  || Order.asc("martType")
        "martType:desc" || Order.desc("martType")
    }

    @Unroll("#order => nothing")
    def "Shouldn't parse order from invalid format"() {
        when:
        def actual = MartOrderParser.parse(order)

        then:
        actual == Optional.empty()

        where:
        order          | _
        null           | _
        ""             | _
        ":asc"         | _
        "foo"          | _
        "foo:bar"      | _
        "foo:asc"      | _
        "martType|asc" | _
    }
}
