package com.hongsi.martholidayalarm.api.mart.controller;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hongsi.martholidayalarm.api.mart.dto.MartResponseDto;
import com.hongsi.martholidayalarm.api.mart.service.BranchApiService;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BranchApiController.class)
public class BranchApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BranchApiService branchApiService;

	@Test
	public void 여러개의_아이디로_마트_지점_조회() throws Exception {
		when(branchApiService.getMartsById(any(List.class)))
				.thenReturn(asList(
						MartResponseDto.builder().id(2L).martType(MartType.EMART.getName()).build(),
						MartResponseDto.builder().id(3L).martType(MartType.LOTTEMART.getName()).build()
				));

		mockMvc.perform(get("/api/branches/2,3")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void 잘못된_아이디_오류_검증() throws Exception {
		mockMvc.perform(get("/api/branches/fail")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
}