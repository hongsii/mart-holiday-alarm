package com.hongsi.martholidayalarm.mart.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.repository.MartRepository;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MartServiceTest {

	@Autowired
	MartService martService;
	@Autowired
	MartRepository martRepository;

	@After
	public void cleanup() {
		martRepository.deleteAll();
	}

	@Test
	public void 타입과realId로_기존값조회_및_마트생성() {
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		martRepository.save(mart);
		Mart savedMart = martService.createMart(MartType.EMART, "1");
		assertThat(mart.getId(), is(savedMart.getId()));

		martRepository.save(savedMart);
		List<Mart> marts = martRepository.findAll();
		assertEquals(1, marts.size());

		Mart createdMart = martService.createMart(MartType.EMART, "2");
		assertNull(createdMart.getId());
	}
}