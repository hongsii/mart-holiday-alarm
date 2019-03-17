package com.hongsi.martholidayalarm.scheduler;

import com.hongsi.martholidayalarm.constants.ProfileType;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartType;
import com.hongsi.martholidayalarm.domain.crawler.MartCrawler;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.service.MartService;
import com.hongsi.martholidayalarm.utils.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
// TODO 개발 서버 구축시 삭제
@Profile({ProfileType.PROD1, ProfileType.PROD2})
public class MartCrawlerScheduler {

	private final MartService martService;

	@Scheduled(cron = "${schedule.cron.crawler:0 0 3 ? * *}")
	public void crawlMart() {
		for (CrawlerMartType crawlerMartType : CrawlerMartType.values()) {
			MartCrawler martCrawler = ApplicationContextUtils.getApplicationContext()
					.getBean(crawlerMartType.getMartCrawler());

			List<Mart> savedMarts = martService.saveCrawlerMarts(martCrawler.crawl());

			log.info("[CRAWLING][COUNT] MartType : {}, crawl count : {}", crawlerMartType,
					savedMarts.size());
		}
	}
}
