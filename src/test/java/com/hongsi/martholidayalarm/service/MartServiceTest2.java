package com.hongsi.martholidayalarm.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartTest;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.repository.MartRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MartServiceTest2 {

	@Autowired
	private MartService martService;

	@Autowired
	private MartRepository martRepository;

	@After
	public void clearAll() {
		martRepository.deleteAll();
	}

	@Test
	public void 같은_고유아이디와_타입이_없으면_저장() {
		Mart newMart = Mart.builder()
				.realId("10")
				.martType(MartType.EMART)
				.branchName("테스트점")
				.build();

		assertThat(martService.save(newMart)).isEqualTo(newMart);
	}

	@Test
	public void 같은_고유아이디와_타입이_있으면_업데이트() {
		Mart savedMart = martRepository.save(MartTest.newMart);

		Mart updateMart = Mart.builder()
				.realId(savedMart.getRealId())
				.martType(savedMart.getMartType())
				.branchName("업데이트")
				.build();

		assertThat(martService.save(updateMart)).isEqualTo(savedMart);
	}
}