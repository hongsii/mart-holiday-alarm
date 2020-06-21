package com.hongsi.martholidayalarm.crawler.model.lottemart

import com.hongsi.martholidayalarm.core.mart.MartType
import spock.lang.Specification

class LotteMartCrawlerTest extends Specification {

    def "crawl"() {
        given:
        def crawler = new LotteMartCrawler()

        when:
        def marts = crawler.crawl()

        then:
        !marts.isEmpty()
        marts.every { it.getMartType() == MartType.LOTTEMART }
        marts.each { println it.toString() }
    }
}
