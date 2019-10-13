package com.hongsi.martholidayalarm.scheduler.crawler.model.mart

import com.hongsi.martholidayalarm.domain.mart.MartType
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
