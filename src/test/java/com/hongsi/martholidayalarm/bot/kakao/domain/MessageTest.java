package com.hongsi.martholidayalarm.bot.kakao.domain;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.time.LocalDate;
import org.junit.Test;

public class MessageTest {

	@Test
	public void makeBranchInfo() {
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1000")
				.region("서울")
				.branchName("성수점")
				.address("서울 성동구 뚝섬로 379")
				.phoneNumber("02-3408-1234")
				.url("http://store.emart.com/branch/list.do?id=1038")
				.holidays(asList(Holiday.builder().date(LocalDate.of(2018, 6, 20)).build()))
				.build();

		Message message = new Message(mart);
		assertThat(message.getText()).isNotBlank();
	}
}