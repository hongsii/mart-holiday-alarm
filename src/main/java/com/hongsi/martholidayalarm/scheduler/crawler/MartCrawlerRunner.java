package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawledMart;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler;
import com.hongsi.martholidayalarm.service.MartService;
import com.hongsi.martholidayalarm.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MartCrawlerRunner implements Runnable {

    private final MartService martService;
    private final LocationConvertClient locationConvertClient;
    private final CrawlerMartType crawlerMartType;

    public MartCrawlerRunner(MartService martService,
                             LocationConvertClient locationConvertClient,
                             CrawlerMartType crawlerMartType) {
        this.martService = martService;
        this.locationConvertClient = locationConvertClient;
        this.crawlerMartType = crawlerMartType;
    }

    @Override
    public void run() {
        MartCrawler martCrawler = ApplicationContextUtils.getApplicationContext()
                .getBean(crawlerMartType.getMartCrawler());

        List<Mart> crawledMarts = martCrawler.crawl()
                .stream()
                .map(CrawledMart::parse)
                .peek(this::convertLocationIfEmpty)
                .map(CrawledMart::toEntity)
                .collect(Collectors.toList());

        List<Mart> savedMarts = martService.saveAll(crawledMarts);
        log.info("[CRAWLING] MartType : {}, count : {}", crawlerMartType, savedMarts.size());
    }

    private void convertLocationIfEmpty(CrawledMart crawledMart) {
        if (crawledMart.hasLocation()) {
            return;
        }

        LocationConvertResult result = locationConvertClient.convert(crawledMart);
        crawledMart.setLocation(result.getLocation());
    }
}
