package com.hongsi.martholidayalarm.scheduler.crawler.domain;

import com.hongsi.martholidayalarm.domain.mart.MartType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class InvalidCrawledMartTest {

    @Test
    public void create_mustPassMartTypeAndRealId() {
        assertThatIllegalArgumentException()
                .describedAs("Both parameter is null")
                .isThrownBy(() -> InvalidCrawledMart.builder().build());
        assertThatIllegalArgumentException()
                .describedAs("realId is null")
                .isThrownBy(() -> InvalidCrawledMart.builder().martType(MartType.EMART).build());
        assertThatIllegalArgumentException()
                .describedAs("martType is null")
                .isThrownBy(() -> InvalidCrawledMart.builder().realId("1").build());
    }

    @Test
    public void isInvalid_ifAllIsSameValue() {
        InvalidCrawledMart invalidCrawledMart = InvalidCrawledMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .build();

        assertThat(invalidCrawledMart.isInvalid(MartType.EMART, "1")).isTrue();
    }

    @Test
    public void isInvalid_ifAnyoneIsDifferentAtLeastItIsValid() {
        InvalidCrawledMart invalidCrawledMart = InvalidCrawledMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .build();

        assertThat(invalidCrawledMart.isInvalid(MartType.EMART, "2")).isFalse();
        assertThat(invalidCrawledMart.isInvalid(MartType.LOTTEMART, "1")).isFalse();
        assertThat(invalidCrawledMart.isInvalid(MartType.LOTTEMART, "2")).isFalse();
    }

    @Test
    public void isInvalid_ifDisableAlwaysValid() {
        InvalidCrawledMart invalidCrawledMart = InvalidCrawledMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .enable(false)
                .build();

        assertThat(invalidCrawledMart.isInvalid(MartType.EMART, "1")).isFalse();
        assertThat(invalidCrawledMart.isInvalid(MartType.LOTTEMART, "1")).isFalse();
    }
}