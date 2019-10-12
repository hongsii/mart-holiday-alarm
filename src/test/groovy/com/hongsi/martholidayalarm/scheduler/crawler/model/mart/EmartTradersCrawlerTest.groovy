package com.hongsi.martholidayalarm.scheduler.crawler.model.mart

import com.hongsi.martholidayalarm.domain.mart.MartType
import spock.lang.Specification

class EmartTradersCrawlerTest extends Specification {

    def "crawl"() {
        given:
        def crawler = new EmartTradersCrawler()

        when:
        def marts = crawler.crawl()

        then:
        !marts.isEmpty()
        marts.every { it.getMartType() == MartType.EMART_TRADERS }
        marts.each { println it.toString() }
    }
}
