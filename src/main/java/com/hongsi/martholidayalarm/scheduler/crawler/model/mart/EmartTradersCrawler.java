package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmartTradersCrawler extends EmartCommonCrawler {

    @Override
    public List<Crawlable> crawl() {
        List<EmartData> marts = crawl(SearchType.TRADERS);
        return new ArrayList<>(marts);
    }
}