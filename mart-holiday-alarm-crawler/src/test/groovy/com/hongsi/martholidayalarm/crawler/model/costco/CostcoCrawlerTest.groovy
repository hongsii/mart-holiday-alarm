package com.hongsi.martholidayalarm.crawler.model.costco

import com.hongsi.martholidayalarm.core.mart.MartType
import spock.lang.Specification

class CostcoCrawlerTest extends Specification {

    def "crawl"() {
        given:
        def crawler = new CostcoCrawler()

        when:
        def marts = crawler.crawl()

        then:
        !marts.isEmpty()
        marts.every { it.getMartType() == MartType.COSTCO }
        marts.each { println it.toString() }
        marts.each { println it.holidays }
    }
}
