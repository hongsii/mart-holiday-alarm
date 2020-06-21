package com.hongsi.martholidayalarm.core.mart

import spock.lang.Specification
import spock.lang.Unroll

import static com.hongsi.martholidayalarm.core.mart.MartType.*

class MartTypeTest extends Specification {

    @Unroll
    def "한글명으로 코드를 찾을 수 있다 [#name -> #expected]"() {
        expect:
        of(name) == expected

        where:
        name         || expected
        "이마트"        || EMART
        "이마트 트레이더스"  || EMART_TRADERS
        "노브랜드"       || NOBRAND
        "롯데마트"       || LOTTEMART
        "홈플러스"       || HOMEPLUS
        "홈플러스 익스프레스" || HOMEPLUS_EXPRESS
        "코스트코"       || COSTCO
    }

    def "한글명과 일치하는 코드가 없으면 에러가 발생한다"() {
        given:
        def name = "리마트"

        when:
        of(name)

        then:
        thrown(IllegalArgumentException)
    }
}
