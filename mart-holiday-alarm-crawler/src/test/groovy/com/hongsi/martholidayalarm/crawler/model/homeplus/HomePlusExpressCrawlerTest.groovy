package com.hongsi.martholidayalarm.crawler.model.homeplus

import com.hongsi.martholidayalarm.core.mart.MartType
import spock.lang.Specification

class HomePlusExpressCrawlerTest extends Specification {

    def "crawl"() {
        given:
        def crawler = new HomePlusExpressCrawler()

        when:
        def marts = crawler.crawl()

        then:
        !marts.isEmpty()
        marts.every { it.getMartType() == MartType.HOMEPLUS_EXPRESS }
        marts.each { println it.toString() }
    }
}
