package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.crawler.model.CrawledMart;
import com.hongsi.martholidayalarm.crawler.utils.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MartCrawlerAsyncService {

    private final MartCrawlerService martCrawlerService;

    @Async("crawlerThreadPool")
    public void crawl(MartCrawlerType crawlerMartType) {
        MartCrawler martCrawler = ApplicationContextUtils.getApplicationContext().getBean(crawlerMartType.getMartCrawler());
        List<CrawledMart> crawledMarts = martCrawler.crawl().stream()
                .map(CrawledMart::parse)
                .collect(Collectors.toList());
        martCrawlerService.saveCrawledMarts(crawledMarts);
    }
}
