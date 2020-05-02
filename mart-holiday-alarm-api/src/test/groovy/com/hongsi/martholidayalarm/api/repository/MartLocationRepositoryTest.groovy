package com.hongsi.martholidayalarm.api.repository

import com.hongsi.martholidayalarm.core.holiday.Holiday
import com.hongsi.martholidayalarm.core.location.Location
import com.hongsi.martholidayalarm.core.mart.Mart
import com.hongsi.martholidayalarm.core.mart.MartType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@DataJpaTest
@Import(MartLocationRepository.class)
class MartLocationRepositoryTest extends Specification {

    @PersistenceContext
    private EntityManager em

    @Autowired
    private MartLocationRepository martLocationRepository

    def "위경도로 마트 다건 조회"() {
        given:
        def target = Mart.builder()
                .martType(MartType.EMART)
                .realId("1")
                .region("서울")
                .branchName("신도림점")
                .holidays([Holiday.of(2020, 3, 1)].toSet())
                .location(Location.of(37.507631, 126.890203))
                .build()
        def nonTarget = Mart.builder()
                .martType(MartType.EMART)
                .realId("2")
                .region("서울")
                .branchName("성수점")
                .holidays([Holiday.of(2020, 3, 2)].toSet())
                .location(Location.of(37.539993, 127.053111))
                .build()
        em.persist(nonTarget)
        em.persist(target)

        when:
        def marts = martLocationRepository.findAllByLocation(37.506872, 126.867378, 3)

        then:
        marts.size() == 1
        marts[0].branchName == target.branchName
    }
}
