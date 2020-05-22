package com.hongsi.martholidayalarm.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class MartCrawlerScheduler {

	private final MartCrawlerAsyncService martCrawlerAsyncService;
	private final ThreadPoolTaskExecutor crawlerThreadPool;

	@PostConstruct
	public void init() {
		log.info("started mart crawler scheduler");
	}

	@Scheduled(cron = "${schedule.cron.crawler:0 0 3 ? * *}")
	public void crawlMarts() throws Exception {
		startCrawlers();
		awaitCrawlers();
	}

	private void startCrawlers() {
		Arrays.stream(MartCrawlerType.values())
				.forEach(martCrawlerAsyncService::crawl);
	}

	private void awaitCrawlers() throws Exception {
		Duration threadWaiting = Duration.ofSeconds(20);
		while (true) {
			int activeCount = crawlerThreadPool.getActiveCount();
			if (activeCount == 0) break;

			log.info("[CRAWLING] wait for crawlers to finish. active count : {}", activeCount);
			Thread.sleep(threadWaiting.toMillis());
		}
	}
}
