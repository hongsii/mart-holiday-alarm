package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.crawler.domain.CrawlerMartType;
import com.hongsi.martholidayalarm.crawler.domain.MartCrawler;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MartCrawlerScheduler {

	private final ApplicationContext applicationContext;
	private final MartService martService;

	@Scheduled(cron = "${schedule.cron.crawler:0 0 3 ? * *}")
	public void crawlMart() {
		for (CrawlerMartType crawlerMartType : CrawlerMartType.values()) {
			MartCrawler martCrawler = applicationContext.getBean(crawlerMartType.getMartCrawler());
			List<Mart> marts = martService.saveMartData(martCrawler.crawl());

			log.info("[CRAWLING][COUNT] MartType : {}, crawl count : {}", crawlerMartType,
					marts.size());
		}
	}
}
