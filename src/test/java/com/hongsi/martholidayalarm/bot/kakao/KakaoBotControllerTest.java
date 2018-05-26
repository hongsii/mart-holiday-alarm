package com.hongsi.martholidayalarm.bot.kakao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hongsi.martholidayalarm.bot.kakao.domain.Keyboard;
import com.hongsi.martholidayalarm.bot.kakao.domain.UserRequest;
import com.hongsi.martholidayalarm.bot.kakao.repository.KakaoBotRepository;
import com.hongsi.martholidayalarm.mart.service.MartService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class KakaoBotControllerTest {

	@Autowired
	MartService martService;
	@Autowired
	KakaoBotRepository kakaoBotRepository;

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void 최초_요청시_키보드_확인() throws Exception {
		HttpHeaders headers = new HttpHeaders();

		mockMvc.perform(get("/keyboard")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", Matchers.is("buttons")))
				.andExpect(jsonPath("$.buttons", new Object[]{AdditionalMatchers
						.aryEq(Keyboard.DEFAULT_KEYBOARD)})
						.isArray());
	}

	@Test
	public void 채팅방_나갈시_이전_유저요청_삭제() throws Exception {
		kakaoBotRepository.save(UserRequest.builder()
				.userKey("1234").build());

		mockMvc.perform(delete("/chat_room/{user_key}", "1234")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());

		Assert.assertNull(kakaoBotRepository.findByUserKey("1234"));
	}
}