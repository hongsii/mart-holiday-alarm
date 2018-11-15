package com.hongsi.martholidayalarm.api.mart.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.hongsi.martholidayalarm.api.exception.ResourceNotFoundException;
import com.hongsi.martholidayalarm.api.mart.dto.MartResponseDto;
import com.hongsi.martholidayalarm.api.mart.repository.MartApiRepository;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class BranchApiServiceTest {

	BranchApiService branchApiService;

	@MockBean
	MartApiRepository martApiRepository;

	@Before
	public void setUp() {
		branchApiService = new BranchApiService(martApiRepository);
	}

	@Test
	public void 하나의_아이디로_마트_조회() throws Exception {
		Mart mart = Mart.builder().id(1L).martType(MartType.LOTTEMART).realId("1").build();

		final Long id = 1L;
		when(martApiRepository.findById(id)).thenReturn(Optional.of(mart));
		MartResponseDto savedMart = branchApiService.getMartById(id);

		assertThat(savedMart.getId()).isEqualTo(id);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void 하나의_아이디로_마트_조회_존재하지_않을때() throws Exception {
		final Long id = 1L;
		when(martApiRepository.findById(id)).thenReturn(Optional.empty());
		branchApiService.getMartById(id);
	}

	@Test
	public void 여러개의_아이디로_마트_조회() throws Exception {
		Mart mart1 = Mart.builder().id(2L).martType(MartType.LOTTEMART).realId("2").build();
		Mart mart2 = Mart.builder().id(3L).martType(MartType.LOTTEMART).realId("3").build();

		when(martApiRepository.findAllById(any(List.class))).thenReturn(asList(mart1, mart2));
		List<MartResponseDto> marts = branchApiService.getMartsById(asList(2L, 3L));

		assertThat(marts).hasSize(2);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void 여러개의_아이디로_마트_조회시_존재하지_않을때() throws Exception {
		Mart mart1 = Mart.builder().id(2L).martType(MartType.LOTTEMART).realId("2").build();
		Mart mart2 = Mart.builder().id(3L).martType(MartType.LOTTEMART).realId("3").build();

		when(martApiRepository.findAllById(any(List.class))).thenReturn(asList());
		branchApiService.getMartsById(asList(2L, 3L));
	}
}