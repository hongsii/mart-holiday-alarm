package com.hongsi.martholidayalarm.mart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartTest;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.repository.MartRepository;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MartServiceTest {

	@Mock
	private MartRepository martRepository;

	@InjectMocks
	private MartService martService;

	@Test
	public void 같은_realId와MartType이_존재하지않으면_저장() {
		Mart newMart = MartTest.newMart;
		when(martRepository.findByRealIdAndMartType(newMart.getRealId(), newMart.getMartType()))
				.thenReturn(Optional.empty());

		martService.save(newMart);

		verify(martRepository, times(1)).save(newMart);
	}

	@Test
	public void 같은_realId와MartType이_존재하면_업데이트() {
		Mart newMart = Mart.builder()
				.realId("1").martType(MartType.EMART)
				.branchName("업데이트마트명")
				.build();
		when(martRepository.findByRealIdAndMartType(newMart.getRealId(), newMart.getMartType()))
				.thenReturn(Optional.of(MartTest.newMart));

		Mart updatedMart = martService.save(newMart);

		verify(martRepository, never()).save(newMart);
		assertThat(updatedMart.getBranchName()).isEqualTo(newMart.getBranchName());
	}
}