package com.hongsi.martholidayalarm.scheduler.crawler.model.mart

import spock.lang.Specification

import static com.hongsi.martholidayalarm.scheduler.crawler.model.mart.EmartData.StoreType
import static com.hongsi.martholidayalarm.scheduler.crawler.model.mart.EmartData.builder

class EmartDataTest extends Specification {

    def "Should remove prefix branch name"() {
        given:
        EmartData martData = builder()
                .storeType(storeType)
                .branchName(branchName)
                .build()

        expect:
        martData.getBranchName() == expected

        where:
        storeType         | branchName       || expected
        StoreType.EMART   | "고양점"           || "고양점"
        StoreType.TRADERS | "고양점"           || "고양점"
        StoreType.TRADERS | "트레이더스 고양점"   || "고양점"
        StoreType.NOBRAND | "판교알파돔점"       || "판교알파돔점"
        StoreType.NOBRAND | "노브랜드 판교알파돔점" || "판교알파돔점"
    }
}
