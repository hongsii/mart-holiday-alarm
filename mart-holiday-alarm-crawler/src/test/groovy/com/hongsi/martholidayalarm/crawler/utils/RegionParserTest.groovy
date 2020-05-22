package com.hongsi.martholidayalarm.crawler.utils


import spock.lang.Specification
import spock.lang.Unroll

class RegionParserTest extends Specification {

    @Unroll
    def "Should parse region"() {
        expect:
        RegionParser.getRegionFromAddress(region) == expected

        where:
        region  || expected
        "서울광역시" || "서울"
        "부산광역시" || "부산"
        "경기도"   || "경기"
        "강원도"   || "강원"
        "경상북도"  || "경북"
        "경상남도"  || "경남"
        "충청북도"  || "충북"
        "충청남도"  || "충남"
        "전라북도"  || "전북"
        "전라남도"  || "전남"
    }
}
