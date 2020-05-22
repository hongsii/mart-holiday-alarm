package com.hongsi.martholidayalarm.crawler.model.emart

import com.hongsi.martholidayalarm.core.mart.MartType
import spock.lang.Specification

class EmartCrawlerTest extends Specification {

    def "crawl"() {
        given:
        def crawler = new EmartCrawler()

        when:
        def marts = crawler.crawl()

        then:
        !marts.isEmpty()
        marts.every { it.getMartType() == MartType.EMART }
        marts.each { println it.toString() }
    }
}
