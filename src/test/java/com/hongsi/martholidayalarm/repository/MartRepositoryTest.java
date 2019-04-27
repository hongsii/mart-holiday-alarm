package com.hongsi.martholidayalarm.repository;

import com.hongsi.martholidayalarm.config.JPAConfig;
import com.hongsi.martholidayalarm.domain.mart.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JPAConfig.class)
public class MartRepositoryTest {

	@Autowired
	private MartRepository martRepository;

	@Before
	public void setUp() throws Exception {
        martRepository.deleteAll();
	}

	@Test
	public void 타입과_realId가_다르면_저장() {
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

	@Test(expected = DataIntegrityViolationException.class)
	public void 타입과_realId가_같으면_에러() {
		Mart savedMart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		martRepository.save(savedMart);

		martRepository.save(Mart.builder()
				.martType(savedMart.getMartType())
				.realId(savedMart.getRealId())
				.build());
	}

	@Test
	public void 휴일로_마트_조회() {
		LocalDate now = LocalDate.now().plusDays(1);
		Holiday parameter = Holiday.of(now);
		Mart expected = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.branchName("foo점")
				.holidays(MartTest.createHolidays(parameter, Holiday.of(now.plusDays(5))))
				.build();
		martRepository.save(expected);
		martRepository.save(Mart.builder()
				.martType(MartType.EMART)
				.realId("2")
				.branchName("foo2점")
				.holidays(MartTest.createHolidays(Holiday.of(now.plusDays(10))))
				.build());

		List<Mart> marts = martRepository.findAllByHolidayInnerJoinHoliday(parameter);

		assertThat(marts).containsOnly(expected);
	}

	@Test
	public void 좌표로_근처_마트_조회() {
		List<Mart> savedMarts = asList(
				Mart.builder()
						.martType(MartType.EMART)
						.realId("1")
						.location(Location.of(36.110000, 127.050000))
						.build(),
				Mart.builder()
						.martType(MartType.LOTTEMART)
						.realId("2")
						.location(Location.of(36.100000, 127.000000))
						.build(),
				Mart.builder()
						.martType(MartType.HOMEPLUS)
						.realId("3")
						.location(Location.of(36.200000, 127.080000))
						.build()
		);
		martRepository.saveAll(savedMarts);

		List<Mart> marts = martRepository.findAllByLocation(
				Location.of(36.100000, 127.000000), 5
		);

		assertThat(marts).hasSize(2).containsExactly(savedMarts.get(1), savedMarts.get(0));
	}
}