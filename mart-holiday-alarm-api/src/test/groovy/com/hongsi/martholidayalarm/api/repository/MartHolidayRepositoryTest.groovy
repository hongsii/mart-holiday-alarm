package com.hongsi.martholidayalarm.api.repository

import com.hongsi.martholidayalarm.api.dto.mart.MartDto
import com.hongsi.martholidayalarm.api.dto.mart.MartTypeDto
import com.hongsi.martholidayalarm.core.holiday.Holiday
import com.hongsi.martholidayalarm.core.mart.Mart
import com.hongsi.martholidayalarm.core.mart.MartType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Sort
import spock.lang.Specification

import java.time.LocalDate

@DataJpaTest
class MartHolidayRepositoryTest extends Specification {

    @Autowired
    private MartHolidayRepository martHolidayRepository

    def "정렬된 마트 다건 조회"() {
        given:
        martHolidayRepository.saveAll([
                Mart.builder()
                        .martType(MartType.EMART)
                        .realId("1")
                        .region("서울")
                        .branchName("신도림점")
                        .holidays([Holiday.of(LocalDate.now())].toSet())
                        .build(),
                Mart.builder()
                        .martType(MartType.EMART)
                        .realId("2")
                        .region("부산")
                        .branchName("구서점")
                        .holidays([Holiday.of(LocalDate.now())].toSet())
                        .build(),
                Mart.builder()
                        .martType(MartType.EMART)
                        .realId("3")
                        .region("서울")
                        .branchName("구로점")
                        .holidays([Holiday.of(LocalDate.now())].toSet())
                        .build(),
        ])

        when:
        def marts = martHolidayRepository.findAllOrderBy(Sort.by(Sort.Order.asc("branchName")))

        then:
        marts[0].branchName == "구로점"
        marts[1].branchName == "구서점"
        marts[2].branchName == "신도림점"
    }

    def "아이디로 정렬된 마트 다건 조회"() {
        given:
        def nonTarget = Mart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .region("서울")
                .branchName("신도림점")
                .holidays([Holiday.of(LocalDate.now())].toSet())
                .build()
        def target1 = Mart.builder()
                .martType(MartType.EMART)
                .realId("2")
                .region("부산")
                .branchName("구서점")
                .holidays([Holiday.of(LocalDate.now())].toSet())
                .build()
        def target2 = Mart.builder()
                .martType(MartType.EMART)
                .realId("3")
                .region("서울")
                .branchName("구로점")
                .holidays([Holiday.of(LocalDate.now())].toSet())
                .build()
        martHolidayRepository.saveAll([nonTarget, target1, target2])

        when:
        def marts = martHolidayRepository.findAllById([target1.id, target2.id], Sort.by(Sort.Order.asc("branchName")))

        then:
        marts[0].branchName == "구로점"
        marts[1].branchName == "구서점"
    }

    def "휴일로 다건 조회"() {
        given:
        def nonTarget = Mart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .region("서울")
                .branchName("신도림점")
                .holidays([Holiday.of(2020, 3, 1)].toSet())
                .build()
        def target = Mart.builder()
                .martType(MartType.EMART)
                .realId("2")
                .region("서울")
                .branchName("구로점")
                .holidays([Holiday.of(2020, 3, 2)].toSet())
                .build()
        martHolidayRepository.saveAll([nonTarget, target])

        when:
        def marts = martHolidayRepository.findAllByHoliday(target.holidays[0])

        then:
        marts.size() == 1
        marts[0] == new MartDto(target)
    }

    def "마트타입으로 마트 다건 조회"() {
        given:
        def nonTarget = Mart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .region("서울")
                .branchName("신도림점")
                .holidays([Holiday.of(LocalDate.now())].toSet())
                .build()
        def target1 = Mart.builder()
                .martType(MartType.COSTCO)
                .realId("2")
                .region("부산")
                .branchName("구서점")
                .holidays([Holiday.of(LocalDate.now())].toSet())
                .build()
        def target2 = Mart.builder()
                .martType(MartType.COSTCO)
                .realId("3")
                .region("서울")
                .branchName("구로점")
                .holidays([Holiday.of(LocalDate.now())].toSet())
                .build()
        martHolidayRepository.saveAll([nonTarget, target1, target2])

        def martType = MartType.COSTCO

        when:
        def marts = martHolidayRepository.findAllByMartType(martType, Sort.by(Sort.Order.asc("branchName")))

        then:
        marts.findAll { MartType.of(it.martType) == martType }.size() == 2
        marts[0].branchName == "구로점"
        marts[1].branchName == "구서점"
    }

    def "저장된 마트타입 조회"() {
        given:
        martHolidayRepository.saveAll([
                Mart.builder()
                        .martType(MartType.EMART)
                        .realId("1")
                        .region("서울")
                        .branchName("신도림점")
                        .holidays([Holiday.of(LocalDate.now())].toSet())
                        .build(),
                Mart.builder()
                        .martType(MartType.EMART)
                        .realId("2")
                        .region("서울")
                        .branchName("성수점")
                        .holidays([Holiday.of(LocalDate.now())].toSet())
                        .build(),
                Mart.builder()
                        .martType(MartType.EMART_TRADERS)
                        .realId("3")
                        .region("부산")
                        .branchName("구서점")
                        .holidays([Holiday.of(LocalDate.now())].toSet())
                        .build(),
                Mart.builder()
                        .martType(MartType.COSTCO)
                        .realId("4")
                        .region("서울")
                        .branchName("구로점")
                        .holidays([Holiday.of(LocalDate.now())].toSet())
                        .build()
        ])

        when:
        def martTypes = martHolidayRepository.findAllMartTypes()

        then:
        def expectedTypes = [MartType.EMART, MartType.EMART_TRADERS, MartType.COSTCO].collect { new MartTypeDto(it) }
        martTypes == martTypes.findAll { expectedTypes.contains(it) }
    }
}
