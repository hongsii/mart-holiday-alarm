package com.hongsi.martholidayalarm.crawler.model.emart

import com.hongsi.martholidayalarm.core.mart.MartType
import spock.lang.Specification

class NobrandCrawlerTest extends Specification {

    def "crawl"() {
        given:
        def crawler = new NobrandCrawler()

        when:
        def marts = crawler.crawl()

        then:
        !marts.isEmpty()
        marts.every { it.getMartType() == MartType.NOBRAND }
        marts.each { println it.toString() }
    }
}
