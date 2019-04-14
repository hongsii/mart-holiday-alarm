package com.hongsi.martholidayalarm.service;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartTest;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.domain.push.PushMart;
import com.hongsi.martholidayalarm.repository.MartRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MartServiceIntegrationTest {

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

	@Test
	public void findPushMartsByHoliday() {
		Holiday target = Holiday.of(LocalDate.now().plusDays(5));
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
                .holidays(MartTest.createHolidays(
						Holiday.of(LocalDate.now()),
						target
				))
				.build();
		martRepository.save(mart);

		List<PushMart> pushMarts = martService.findPushMartsByHoliday(target);

		PushMart expected = PushMart.builder()
				.id(mart.getId())
                .holiday(target)
				.build();
		assertThat(pushMarts).containsExactly(expected);
	}
}