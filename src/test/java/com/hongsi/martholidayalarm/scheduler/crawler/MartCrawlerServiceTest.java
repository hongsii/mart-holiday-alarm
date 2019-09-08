package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawledMart;
import com.hongsi.martholidayalarm.service.MartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class MartCrawlerServiceTest {

    @Mock
    private MartService martService;
    @Mock
    private LocationConvertClient locationConvertClient;

    @InjectMocks
    private MartCrawlerService martCrawlerService;

    @Test
    public void testConvertLocation_ifLocationIsEmptyConvert() {
        CrawledMart target = CrawledMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .branchName("위치가 없는 마트")
                .region("서울")
                .address("주소")
                .holidays(new ArrayList<>())
                .holidayText("매주 월요일 휴무")
                .openingHours("10:00 ~ 11:00")
                .phoneNumber("010-1234-5678")
                .url("https://github.io/hongsii")
                .location(null)
                .build();
        CrawledMart nonTarget = CrawledMart.builder()
                .martType(MartType.EMART)
                .realId("2")
                .branchName("위치가 있는 마트")
                .region("서울")
                .address("주소")
                .holidays(new ArrayList<>())
                .holidayText("매주 월요일 휴무")
                .openingHours("10:00 ~ 11:00")
                .phoneNumber("010-1234-5678")
                .url("https://github.io/hongsii")
                .location(Location.of(30D, 60D))
                .build();
        when(locationConvertClient.convert(target)).thenReturn(new LocationConvertResult());

        martCrawlerService.saveCrawledMarts(Arrays.asList(target, nonTarget));

        verify(locationConvertClient, times(1)).convert(target);
        verify(martService, times(1)).saveAll(anyList());
    }
}