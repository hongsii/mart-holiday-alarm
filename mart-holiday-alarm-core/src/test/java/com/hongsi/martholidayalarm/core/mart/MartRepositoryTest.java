package com.hongsi.martholidayalarm.core.mart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MartRepositoryTest {

	@Autowired
	private MartRepository martRepository;

	@Before
	public void setUp() {
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
}