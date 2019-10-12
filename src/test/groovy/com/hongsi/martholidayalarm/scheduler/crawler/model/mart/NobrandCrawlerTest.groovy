package com.hongsi.martholidayalarm.scheduler.crawler.model.mart

import com.hongsi.martholidayalarm.domain.mart.MartType
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
