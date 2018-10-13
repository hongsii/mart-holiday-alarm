package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import com.hongsi.martholidayalarm.crawler.domain.MartCrawler;
import com.hongsi.martholidayalarm.crawler.domain.MartPage;
import com.hongsi.martholidayalarm.mobile.push.firebase.service.FavoriteService;
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

	private final MartService martService;
	private final FavoriteService favoriteService;

	private StopWatch stopWatch;

	@Scheduled(cron = "${schedule.cron.crawler:0 0 3 ? * *}")
	public void crawlMart() {
		log.info("Start Mart crawling");
		stopWatch = new StopWatch("MartCrawler");

		List<Mart> crawledMarts = new ArrayList<>();
		List<MartPage> pages = getPages();
		for (MartPage page : pages) {
			Mart mart = page.getInfo();
			if (!mart.getRealId().isEmpty()) {
				crawledMarts.add(mart);
			}
		}
		martService.saveAll(crawledMarts);

		log.info(stopWatch.prettyPrint());
	}

	private List<MartPage> getPages() {
		List<MartPage> pages = new ArrayList<>();
		for (MartType martType : MartType.values()) {
			stopWatch.start(martType.getName());
			try {
				MartCrawler martCrawler = martType.getMartCrawler();
				List<MartPage> crawledPages = martCrawler.crawl();
				pages.addAll(crawledPages);
				log.info("MartType : {}, Count : {}", martType.getName(), crawledPages.size());
			} catch (Exception e) {
				log.error("[Crawling Error] - Can't crawl url of {} / message : ",
						martType.getName(), e.getMessage());
			}
			stopWatch.stop();
		}
		return pages;
	}

	@Scheduled(cron = "${schedule.cron.delete:0 30 3 ? * *}")
	public void removeNotUpdatedMart() {
		log.info("Start remove not updated Marts");
		int minusDays = 14;
		List<Long> deletedIds = martService.removeNotUpdatedMart(minusDays);
		favoriteService.removeFavoritedMart(deletedIds);
		log.info("End remove not updated Marts");
	}
}
