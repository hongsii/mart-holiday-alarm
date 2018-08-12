package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import com.hongsi.martholidayalarm.crawler.domain.MartCrawler;
import com.hongsi.martholidayalarm.crawler.domain.MartPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@RequiredArgsConstructor
@Slf4j
public class MartCrawlerScheduler {

	private final String logPrefix = "Crawler -- ";

	private final MartService martService;

	private StopWatch stopWatch;

	//	@Scheduled(initialDelay = 9000, fixedDelay = 90000)
	@Scheduled(cron = "0 0 3 ? * MON")
	public void crawlMart() {
		log("start !");
		stopWatch = new StopWatch("martCrawler");

		List<Mart> crawledMarts = new ArrayList<>();
		List<MartPage> pages = getPages();
		for (MartPage page : pages) {
			Mart mart = page.getInfo();
			if (!mart.getRealId().isEmpty()) {
				crawledMarts.add(mart);
			}
		}

		stopWatch.start("insert");
		martService.saveAll(crawledMarts);
		stopWatch.stop();

		log(stopWatch.prettyPrint());
	}

	private List<MartPage> getPages() {
		List<MartPage> pages = new ArrayList<>();
		for (MartType martType : MartType.values()) {
			log(martType.getName() + " start !");
			stopWatch.start(martType.getName());
			try {
				MartCrawler martCrawler = martType.getMartCrawler();
				List<MartPage> crawledPages = martCrawler.crawl();
				pages.addAll(crawledPages);

				stopWatch.stop();
				log("Mart type : " + martType +
						" > mart count : " + crawledPages.size());
			} catch (IOException ie) {
				log.error(logPrefix + "Exception : " + martType + " Can't find url");
			}
		}
		return pages;
	}

	private void log(String message) {
		log.info(logPrefix + message);
	}

	@Scheduled(cron = "0 30 3 ? * MON")
	public void deleteNotUpdatedMart() {
		int minusDays = 14;
		log.info("Delete not updated marts : update before {} days", minusDays);
		martService.removeNotUpdatedMart(minusDays);

	}
}
