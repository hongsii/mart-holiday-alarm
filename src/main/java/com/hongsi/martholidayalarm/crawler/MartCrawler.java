package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.crawler.domain.Parser;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class MartCrawler {
	MartService martService;

	@Scheduled(initialDelay = 9000, fixedDelay = 90000)
//	@Scheduled(cron = "0 0 3 ? * MON")
	public void start() throws IOException {
		log.info("Crawler -- start !");
		for (MartType martType : MartType.values()) {
			if (martType.isUsing()) {
				log.info("Crawler -- Mart type : " + martType);
				Parser parser = Parser.create(martType);
				List<Mart> marts = parser.getParsedData();
				martService.saveAll(marts);
				log.info("Crawler -- count : " + marts.size());
			}
		}
		log.info("Crawler -- done !");
	}
}
