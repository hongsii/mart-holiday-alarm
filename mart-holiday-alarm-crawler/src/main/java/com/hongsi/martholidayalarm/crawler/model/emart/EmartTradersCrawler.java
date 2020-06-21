package com.hongsi.martholidayalarm.crawler.model.emart;

import org.springframework.stereotype.Component;

@Component
public class EmartTradersCrawler extends EmartCommonCrawler {

    @Override
    protected SearchType getSearchType() {
        return SearchType.TRADERS;
    }
}