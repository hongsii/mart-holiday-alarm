package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.constants.ProfileType;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawledMart;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler;
import com.hongsi.martholidayalarm.service.MartService;
import com.hongsi.martholidayalarm.utils.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
// TODO 개발 서버 구축시 삭제
@Profile({ProfileType.PROD1, ProfileType.PROD2})
public class MartCrawlerScheduler {

	private final MartService martService;
	private final LocationConvertClient locationConvertClient;

	@Scheduled(cron = "${schedule.cron.crawler:0 0 3 ? * *}")
	public void crawlMart() {
		for (CrawlerMartType crawlerMartType : CrawlerMartType.values()) {
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
	}

	private void convertLocationIfEmpty(CrawledMart crawledMart) {
	    if (crawledMart.hasLocation()) {
	    	return;
		}

		LocationConvertResult result = locationConvertClient.convert(crawledMart);
		crawledMart.setLocation(result.getLocation());
	}
}
