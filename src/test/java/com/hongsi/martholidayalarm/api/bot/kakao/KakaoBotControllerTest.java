package com.hongsi.martholidayalarm.api.bot.kakao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hongsi.martholidayalarm.service.KakaoBotService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = KakaoBotController.class)
@AutoConfigureMockMvc
public class KakaoBotControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private KakaoBotService kakaoBotService;

	private String PREFIX_URL = "/api/bot/kakao";

	@Test
	public void 최초_요청시_키보드_확인() throws Exception {
		mockMvc.perform(get(PREFIX_URL + "/keyboard")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", Matchers.is("buttons")))
				.andExpect(jsonPath("$.buttons[0]").value("마트 휴일 조회"));
	}

	@Test
	public void 채팅방_나갈시_이전_유저요청_삭제() throws Exception {
		mockMvc.perform(delete(PREFIX_URL + "/chat_room/1234")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void 친구삭제시_이전_유저요청_삭제() throws Exception {
		mockMvc.perform(delete(PREFIX_URL + "/friend/1234")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
		verify(kakaoBotService, times(1)).deleteUserRequest(any(String.class));
	}

	@Test
	public void 삭제_요청시_id생략() throws Exception {
		mockMvc.perform(delete(PREFIX_URL + "/friend/")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNotFound());
		verify(kakaoBotService, never()).deleteUserRequest(any(String.class));
	}

	@Configuration
	@ComponentScan(basePackageClasses = {KakaoBotController.class})
	public static class TestConf {

	}
}