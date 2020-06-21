package com.hongsi.martholidayalarm.crawler;

import com.hongsi.martholidayalarm.client.location.converter.LocationConversion;
import com.hongsi.martholidayalarm.client.location.converter.LocationConverter;
import com.hongsi.martholidayalarm.core.mart.Mart;
import com.hongsi.martholidayalarm.core.mart.MartRepository;
import com.hongsi.martholidayalarm.crawler.domain.InvalidCrawledMart;
import com.hongsi.martholidayalarm.crawler.domain.InvalidCrawledMartRepository;
import com.hongsi.martholidayalarm.crawler.model.CrawledMart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MartCrawlerService {

    private final MartRepository martRepository;
    private final InvalidCrawledMartRepository invalidCrawledMartRepository;
    private final LocationConverter locationConverter;

    @Transactional
    public List<Mart> saveCrawledMarts(List<CrawledMart> crawledMarts) {
        List<InvalidCrawledMart> invalidCrawledMarts = invalidCrawledMartRepository.findAllByEnable(true);
        return crawledMarts.stream()
                .filter(crawledMart -> crawledMart.canCrawl(invalidCrawledMarts))
                .peek(this::convertLocationIfEmpty)
                .map(CrawledMart::toEntity)
                .peek(this::save)
                .collect(Collectors.toList());
    }

    private Mart save(Mart mart) {
        return martRepository.findByRealIdAndMartType(mart.getRealId(), mart.getMartType())
                .map(savedMart -> savedMart.update(mart))
                .orElseGet(() -> martRepository.save(mart));
    }

    private void convertLocationIfEmpty(CrawledMart crawledMart) {
        if (crawledMart.hasLocation()) {
            return;
        }

        LocationConversion conversion = locationConverter.convert(
                () -> String.format("%s %s", crawledMart.getMartType().getName(), crawledMart.getBranchName()),
                crawledMart::getAddress
        );
        crawledMart.setLocation(conversion.getLatitude(), conversion.getLongitude());
    }
}
