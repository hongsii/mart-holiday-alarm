package com.hongsi.martholidayalarm.crawler.model.emart;

import org.springframework.stereotype.Component;

@Component
public class EmartCrawler extends EmartCommonCrawler {

    @Override
    protected SearchType getSearchType() {
        return SearchType.EMART;
    }
}
