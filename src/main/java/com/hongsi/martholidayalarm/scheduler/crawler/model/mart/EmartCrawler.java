package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmartCrawler extends EmartCommonCrawler {

    @Override
    public List<Crawlable> crawl() {
        return new ArrayList<>(crawl(SearchType.EMART));
    }
}