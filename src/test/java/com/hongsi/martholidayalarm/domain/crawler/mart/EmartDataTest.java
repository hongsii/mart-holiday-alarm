package com.hongsi.martholidayalarm.domain.crawler.mart;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmartDataTest {

    @Test
    public void removePrefixOfBranchNameForTraders() {
        EmartData martData = EmartData.builder()
                .storeType("T")
                .branchName("트레이더스 고양점")
                .build();

        assertThat(martData.getBranchName()).isEqualTo("고양점");
    }

    @Test
    public void shouldNotRemovePrefixOfBranchNameForEmart() {
        EmartData martData = EmartData.builder()
                .storeType("E")
                .branchName("고양점")
                .build();

        assertThat(martData.getBranchName()).isEqualTo("고양점");
    }
}