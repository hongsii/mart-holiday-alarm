package com.hongsi.martholidayalarm.api.dto.mart

import org.springframework.data.domain.Sort
import spock.lang.Specification
import spock.lang.Unroll

import static com.hongsi.martholidayalarm.api.dto.mart.MartOrder.*

class MartSortParserTest extends Specification {

    @Unroll("#orders => #expected")
    def "Should parse sort from valid format"() {
        when:
        def actual = MartSortParser.parse(orders, id.asc())

        then:
        actual == Sort.by(expected)

        where:
        orders                              || expected
        ["martType:asc", "invalid"]         || [martType.asc()]
        ["invalid:asc", "branchName:desc"]  || [branchName.desc()]
        ["martType:asc", "branchName:desc"] || [martType.asc(), branchName.desc()]
    }

    @Unroll("#orders | #defaultValue => #expected")
    def "Should parse sort with empty parameter"() {
        when:
        def actual = MartSortParser.parse(orders, defaultValue)

        then:
        actual == expected

        where:
        orders | defaultValue || expected
        null   | id.asc()     || Sort.by(id.asc())
        null   | null         || Sort.unsorted()
        []     | null         || Sort.unsorted()
    }
}
