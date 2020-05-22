package com.hongsi.martholidayalarm.crawler.model.homeplus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HomePlusExpressCrawler extends HomePlusCommonCrawler {

    @Override
    protected StoreType getStoreType() {
        return StoreType.EXPRESS;
    }
}
