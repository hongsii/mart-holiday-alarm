package com.hongsi.martholidayalarm.scheduler.crawler.domain;

import com.hongsi.martholidayalarm.domain.mart.MartType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidMartTest {

    @Test
    public void isInvalid_ifAllIsSameValue() {
        InvalidMart invalidMart = InvalidMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .build();

        assertThat(invalidMart.isInvalid(MartType.EMART, "1")).isTrue();
    }

    @Test
    public void isInvalid_ifAnyoneIsDifferentAtLeastItIsValid() {
        InvalidMart invalidMart = InvalidMart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .build();

        assertThat(invalidMart.isInvalid(MartType.EMART, "2")).isFalse();
        assertThat(invalidMart.isInvalid(MartType.LOTTEMART, "1")).isFalse();
        assertThat(invalidMart.isInvalid(MartType.LOTTEMART, "2")).isFalse();
    }
}