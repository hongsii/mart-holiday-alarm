package com.hongsi.martholidayalarm.common.mart;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hongsi.martholidayalarm.api.mart.MartController;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MartController.class)
@AutoConfigureMockMvc
public class MartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MartService martService;

	@Test
	public void 마트타입으로_소문자로_리스트_요청() throws Exception {
		mockMvc.perform(get(getApiUrl("/emart/list"))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		verify(martService, times(1)).getMartsByMartType(any(MartType.class));
	}

	@Test
	public void 마트타입으로_대문자로_리스트_요청() throws Exception {
		mockMvc.perform(get(getApiUrl("/emart/list"))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		verify(martService, times(1)).getMartsByMartType(any(MartType.class));
	}

	@Test
	public void 잘못된_마트타입으로_리스트_요청() throws Exception {
		mockMvc.perform(get(getApiUrl("/hongmart/list"))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void 아이디로_마트_지점_조회() throws Exception {
		when(martService.getMartsById(any(List.class)))
				.thenReturn(asList(Mart.builder().id(2L).build()));
		mockMvc.perform(get(getApiUrl("/branch/2"))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void 조회된_지점이_없을때_404() throws Exception {
		mockMvc.perform(get(getApiUrl("/branch/2"))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	private String getApiUrl(String path) {
		return "/api/mart" + path;
	}
}