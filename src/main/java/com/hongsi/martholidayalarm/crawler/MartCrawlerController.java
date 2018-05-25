package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.crawler.domain.MartCrawler;
import com.hongsi.martholidayalarm.crawler.domain.MartPage;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class MartCrawlerController {

	MartService martService;

	//	@Scheduled(initialDelay = 9000, fixedDelay = 90000)
	@Scheduled(cron = "0 0 3 ? * MON")
	public void start() throws IOException {
		log.info("Crawler -- start !");

		List<Mart> crawledMarts = new ArrayList<>();
		List<MartPage> pages = getPages();
		for (MartPage page : pages) {
			crawledMarts.add(page.getInfo());
		}
		martService.saveAll(crawledMarts);

		log.info("Crawler -- done !");
	}

	private List<MartPage> getPages() throws IOException {
		List<MartPage> pages = new ArrayList<>();
		for (MartType martType : MartType.values()) {
			try {
				MartCrawler martCrawler = martType.getMartCrawler();
				List<MartPage> crawledPages = martCrawler.crawl();
				pages.addAll(crawledPages);
				log.info("Crawler -- Mart type : " + martType +
						" > page count : " + crawledPages.size());
			} catch (IOException ie) {
				log.error("Crawler Exception : " + martType + " Can't find url");
			}
		}
		return pages;
	}
}
