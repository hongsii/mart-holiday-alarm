package com.hongsi.martholidayalarm.mart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.config.JPAConfig;
import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartTest;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JPAConfig.class)
public class MartRepositoryTest {

	@Autowired
	private MartRepository martRepository;

	@Test
	public void 타입과_realId가_다르면_저장() {
		Mart newMart1 = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		martRepository.save(newMart1);

		Mart newMart = Mart.builder()
				.martType(MartType.LOTTEMART)
				.realId(newMart1.getRealId())
				.build();
		martRepository.save(newMart);

		assertThat(martRepository.findAll())
				.hasSize(2)
				.contains(newMart);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void 타입과_realId가_같으면_에러() {
		Mart savedMart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		martRepository.save(savedMart);

		Mart newMart = Mart.builder()
				.martType(savedMart.getMartType())
				.realId(savedMart.getRealId())
				.build();
		martRepository.save(newMart);
	}

	@Test
	public void 휴일로_마트_조회() {
		LocalDate now = LocalDate.now().plusDays(1);
		Holiday parameter = Holiday.of(now);
		Mart savedMart = Mart.builder()
				.id(1L)
				.martType(MartType.EMART)
				.realId("1")
				.branchName("foo점")
				.holidays(MartTest.createHolidays(parameter, Holiday.of(now.plusDays(5))))
				.build();
		martRepository.save(savedMart);

		assertThat(martRepository.findPushMartsByHoliday(parameter))
				.containsOnly(new PushMart(1L, MartType.EMART, "foo점", parameter));
	}
}