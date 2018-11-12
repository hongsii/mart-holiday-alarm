package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import com.hongsi.martholidayalarm.crawler.domain.MartCrawler;
import com.hongsi.martholidayalarm.crawler.domain.MartPage;
import com.hongsi.martholidayalarm.crawler.exception.CrawlerNotFoundException;
import com.hongsi.martholidayalarm.mobile.push.firebase.service.FavoriteService;
import java.util.ArrayList;
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
	private final FavoriteService favoriteService;

	@Scheduled(cron = "${schedule.cron.crawler:0 0 3 ? * *}")
	public void crawlMart() {
		List<Mart> crawledMarts = new ArrayList<>();
		List<MartPage> pages = getPages();
		for (MartPage page : pages) {
			Mart mart = page.getInfo();
			if (!mart.getRealId().isEmpty()) {
				crawledMarts.add(mart);
			}
		}
		martService.saveAll(crawledMarts);
	}

	private List<MartPage> getPages() {
		List<MartPage> pages = new ArrayList<>();
		for (MartType martType : MartType.values()) {
			try {
				MartCrawler martCrawler = applicationContext.getBean(MartCrawler.of(martType));
				List<MartPage> crawledPages = martCrawler.crawl();
				if (martType.equals(MartType.EMART)) {
					break;
				}
				pages.addAll(crawledPages);
				log.info("MartType : {}, Count : {}", martType.getName(), crawledPages.size());
			} catch (CrawlerNotFoundException ce) {
				log.error("[Crawling Error] - Can't find crawler of {}", martType);
			} catch (Exception e) {
				log.error("[Crawling Error] - Can't crawl url of {} / message : ",
						martType.getName(), e.getMessage());
			}
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
