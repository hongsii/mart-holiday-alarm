package com.hongsi.martholidayalarm.crawler.model.homeplus

import com.hongsi.martholidayalarm.core.mart.MartType
import spock.lang.Specification

class HomePlusCrawlerTest extends Specification {

    def "crawl"() {
        given:
        def crawler = new HomePlusCrawler()

        when:
        def marts = crawler.crawl()

        then:
        !marts.isEmpty()
        marts.every { it.getMartType() == MartType.HOMEPLUS }
        marts.each { println it.toString() }
    }
}
