package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawledMart;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler;
import com.hongsi.martholidayalarm.utils.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class MartCrawlerRunner implements Runnable {

    private final CrawlerMartType crawlerMartType;
    private final MartCrawlerService martCrawlerService;

    @Override
    public void run() {
        MartCrawler martCrawler = ApplicationContextUtils.getApplicationContext()
                .getBean(crawlerMartType.getMartCrawler());
        List<CrawledMart> crawledMarts = martCrawler.crawl().stream()
                .map(CrawledMart::parse)
                .collect(Collectors.toList());
        martCrawlerService.saveCrawledMarts(crawledMarts);
        log.info("[CRAWLING] MartType : {}, count : {}", crawlerMartType, crawledMarts.size());
    }
}
