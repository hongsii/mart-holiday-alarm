package com.hongsi.martholidayalarm.push;

import com.hongsi.martholidayalarm.push.service.MartPusher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"prod1", "prod2"})
public class MartPushScheduler {

	private final MartPusher martPusher;
	private final ThreadPoolTaskExecutor pushThreadPool;

	@PostConstruct
	public void init() {
		log.info("started mart push scheduler");
	}

	@Scheduled(cron = "${schedule.cron.push:0 0 11 ? * *}")
	public void start() throws Exception {
		martPusher.pushToUsers();
		await();
	}

	private void await() throws Exception {
		Duration threadWaiting = Duration.ofSeconds(5);
		while (true) {
			Thread.sleep(threadWaiting.toMillis());
			int activeCount = pushThreadPool.getActiveCount();
			if (activeCount == 0) break;

			log.info("wait to finish for push. active count: {}", activeCount);
		}
	}
}
