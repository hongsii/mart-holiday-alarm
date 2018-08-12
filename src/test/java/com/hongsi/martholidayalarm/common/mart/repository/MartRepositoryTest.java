package com.hongsi.martholidayalarm.common.mart.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.config.JPAConfig;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JPAConfig.class)
public class MartRepositoryTest {

	@Autowired
	MartRepository martRepository;

	@Test
	public void 마트저장_조회() {
		martRepository.save(Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build());
		martRepository.save(Mart.builder()
				.martType(MartType.EMART)
				.realId("2")
				.build());

		List<Mart> marts = martRepository.findAll();

		assertThat(marts.size(), is(2));

		Mart mart = marts.get(0);
		assertThat(mart.getRealId(), is("1"));
		assertThat(mart.getMartType(), is(MartType.EMART));
	}

	@Test
	public void BaseEntity사용() {
		LocalDateTime now = LocalDateTime.now();

		martRepository.save(Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build());

		List<Mart> marts = martRepository.findAll();

		Mart mart = marts.get(0);
		assertTrue(mart.getCreatedDate().isAfter(now));
		assertTrue(mart.getModifiedDate().isAfter(now));
	}

	@Test
	public void Entity업데이트시_CreatedDate_확인() {
		LocalDateTime now = LocalDateTime.now();

		martRepository.save(Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build());

		List<Mart> marts = martRepository.findAll();
		Mart savedMart = marts.get(0);
		assertTrue(savedMart.getCreatedDate().isAfter(now));

		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		martRepository.save(mart);
		marts = martRepository.findAll();
		mart = marts.get(0);
		assertTrue(mart.getCreatedDate().isEqual(mart.getCreatedDate()));
	}

	@Test
	public void Enum저장_확인() {
		martRepository.save(Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build());
		List<Mart> marts = martRepository.findAll();
		Mart savedMart = marts.get(0);
		assertThat(MartType.EMART, is(savedMart.getMartType()));
	}
}