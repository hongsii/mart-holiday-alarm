package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.constants.ProfileType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
// TODO 개발 서버 구축시 삭제
@Profile({ProfileType.PROD1, ProfileType.PROD2})
public class MartCrawlerScheduler {

	private static final int WAIT_FOR_SECONDS = 20 * 1000;

	private final MartCrawlerAsyncService martCrawlerAsyncService;
	private final ThreadPoolTaskExecutor defaultThreadPool;

	@Scheduled(cron = "${schedule.cron.crawler:0 0 3 ? * *}")
	public void crawlMart() throws Exception {
		startCrawlers();
		awaitCrawlers();
	}

	private void startCrawlers() {
		Arrays.stream(CrawlerMartType.values())
				.forEach(martCrawlerAsyncService::crawl);
	}

	private void awaitCrawlers() throws Exception {
		while (true) {
			int activeCount = defaultThreadPool.getActiveCount();
			if (activeCount == 0) break;

			log.info("[CRAWLING] wait for crawlers to finish. active count : {}", activeCount);
			Thread.sleep(WAIT_FOR_SECONDS);
		}
	}
}
