package com.hongsi.martholidayalarm.scheduler.crawler.model;

import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.domain.InvalidCrawledMart;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CrawledMartTest {

    @Test
    public void canCrawl_ifNotContainsInInvalidMartCanCrawl() {
        CrawledMart crawledMart = CrawledMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .build();

        boolean actual = crawledMart.canCrawl(Collections.singletonList(
                InvalidCrawledMart.builder().martType(MartType.EMART).realId("2").build()
        ));

        assertThat(actual).isTrue();
    }

    @Test
    public void canCrawl_ifInvalidCanNotCrawl() {
        CrawledMart crawledMart = CrawledMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .build();

        boolean actual = crawledMart.canCrawl(Arrays.asList(
                InvalidCrawledMart.builder().martType(MartType.EMART).realId("2").build(),
                InvalidCrawledMart.builder().martType(MartType.EMART).realId("1").build()
        ));

        assertThat(actual).isFalse();
    }
}