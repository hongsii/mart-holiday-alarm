package com.hongsi.martholidayalarm.utils

import spock.lang.Specification

class StringUtilsTest extends Specification {

    def "Should replace white space"() {
        expect:
        StringUtils.replaceWhitespace(value) == expected

        where:
        value           || expected
        "공백 문자"        || "공백문자"
        "공백 여러개   문자" || "공백여러개문자"
    }

    def "Should check empty string"() {
        expect:
        StringUtils.isBlank(value) == expected

        where:
        value || expected
        null  ||  true
        ""    ||  true
        "  "  ||  true
        "실패" ||  false
    }
}
