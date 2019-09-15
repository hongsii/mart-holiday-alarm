package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.scheduler.crawler.domain.InvalidMart;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawledMart;
import com.hongsi.martholidayalarm.service.MartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MartCrawlerService {

    private final MartService martService;
    private final InvalidMartRepository invalidMartRepository;
    private final LocationConvertClient locationConvertClient;

   public List<Mart> saveCrawledMarts(List<CrawledMart> crawledMarts) {
       List<InvalidMart> invalidMarts = invalidMartRepository.findAll();
       return martService.saveAll(
               crawledMarts.stream()
                       .filter(crawledMart -> crawledMart.canCrawl(invalidMarts))
                       .peek(this::convertLocationIfEmpty)
                       .map(CrawledMart::toEntity)
                       .collect(Collectors.toList())
       );
   }

    private void convertLocationIfEmpty(CrawledMart crawledMart) {
        if (crawledMart.hasLocation()) {
            return;
        }

        LocationConvertResult result = locationConvertClient.convert(crawledMart);
        crawledMart.setLocation(result.getLocation());
    }
}
