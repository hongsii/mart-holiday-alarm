package com.hongsi.martholidayalarm.core.mart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class MartRepositoryTest {

    @Autowired
    private MartRepository martRepository;

    @BeforeEach
    public void setUp() {
        martRepository.deleteAll();
    }

    @Test
    void 타입과_realId가_다르면_저장() {
        Mart newMart1 = Mart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .build();
        martRepository.save(newMart1);

        Mart newMart2 = Mart.builder()
                .martType(MartType.LOTTEMART)
                .realId(newMart1.getRealId())
                .build();
        martRepository.save(newMart2);

        assertThat(martRepository.findAll())
                .hasSize(2)
                .contains(newMart2);
    }

    @Test
    void 타입과_realId가_같으면_에러() {
        Mart savedMart = Mart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .build();
        martRepository.save(savedMart);

        assertThatThrownBy(() -> martRepository.save(
                Mart.builder()
                        .martType(savedMart.getMartType())
                        .realId(savedMart.getRealId())
                        .build())
        );
    }
}