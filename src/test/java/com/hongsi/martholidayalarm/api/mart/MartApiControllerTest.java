package com.hongsi.martholidayalarm.api.mart;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(MartApiController.class)
public class MartApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MartService martService;

	@Test
	public void 아이디로_마트_조회() throws Exception {
		mockMvc.perform(get("/api/marts/1,2")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		verify(martService, times(1)).findMartsById(Stream.of(1L, 2L).collect(Collectors.toSet()));
	}

	@Test
	public void 마트타입조회() throws Exception {
		mockMvc.perform(get("/api/marts/type")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		verify(martService, times(1)).findMartTypeNames();
	}

	@Test
	public void 마트타입으로_소문자로_리스트_요청() throws Exception {
		mockMvc.perform(get("/api/marts/type/" + getFirstMartTypeName().toLowerCase())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		verify(martService, times(1)).findMartsByMartType(any());
	}

	@Test
	public void 마트타입으로_대문자로_리스트_요청() throws Exception {
		mockMvc.perform(get("/api/marts/type/" + getFirstMartTypeName().toUpperCase())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		verify(martService, times(1)).findMartsByMartType(any());
	}

	@Test
	public void 잘못된_마트타입으로_리스트_요청() throws Exception {
		mockMvc.perform(get("/api/marts/hongmart")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	private String getFirstMartTypeName() {
		return MartType.values()[0].toString();
	}
}