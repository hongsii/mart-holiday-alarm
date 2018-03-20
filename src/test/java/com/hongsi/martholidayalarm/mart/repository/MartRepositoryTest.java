package com.hongsi.martholidayalarm.mart.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MartRepositoryTest {

	@Autowired
	MartRepository martRepository;

	@After
	public void cleanup() {
		martRepository.deleteAll();
	}

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
}