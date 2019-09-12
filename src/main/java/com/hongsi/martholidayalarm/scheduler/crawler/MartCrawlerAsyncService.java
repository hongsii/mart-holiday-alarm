package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawledMart;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler;
import com.hongsi.martholidayalarm.utils.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class MartCrawlerAsyncService {

    private final MartCrawlerService martCrawlerService;

    @Async("defaultThreadPool")
    public void crawl(CrawlerMartType crawlerMartType) {
        MartCrawler martCrawler = ApplicationContextUtils.getApplicationContext()
                .getBean(crawlerMartType.getMartCrawler());
        List<CrawledMart> crawledMarts = martCrawler.crawl().stream()
                .map(CrawledMart::parse)
                .collect(Collectors.toList());
        martCrawlerService.saveCrawledMarts(crawledMarts);
        log.info("[CRAWLING] MartType : {}, count : {}", crawlerMartType, crawledMarts.size());
    }
}
