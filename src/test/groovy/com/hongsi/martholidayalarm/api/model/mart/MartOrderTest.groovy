package com.hongsi.martholidayalarm.api.model.mart

import com.hongsi.martholidayalarm.api.model.mart.MartOrder
import spock.lang.Specification
import spock.lang.Unroll

class MartOrderTest extends Specification {

    def "Should find mart order of same name"() {
        given:
        def names = MartOrder.values().collect { it.name() }

        when:
        def actual = names.collect { MartOrder.of(it) }

        then:
        actual == MartOrder.values() as List
    }

    @Unroll("#name")
    def "Shouldn't find mart order of case-insensitive name"() {
        when:
        MartOrder.of(name)

        then:
        thrown(IllegalArgumentException)

        where:
        name       | _
        "ID"       | _
        "marttype" | _
        "MARTTYPE" | _
    }
}
