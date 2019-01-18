package com.hongsi.martholidayalarm.api.mart;

import static com.hongsi.martholidayalarm.mart.domain.MartSortBuilder.defaultSort;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.MartResponse;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.util.LinkedHashSet;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@RunWith(SpringRunner.class)
@WebMvcTest(MartApiController.class)
public class MartApiControllerTest {

	private static final String MART_URL = "/api/marts";
	private static final String MART_TYPE_URL = MART_URL + "/types";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MartService martService;

	@Test
	public void 전체_마트_조회() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/api/marts")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void 아이디로_다수_마트_조회() throws Exception {
		List<MartResponse> response = asList(
				MartResponse.builder()
						.id(1L)
						.martType(MartType.EMART)
						.branchName("신도림점")
						.build(),
				MartResponse.builder()
						.id(5L)
						.martType(MartType.EMART)
						.branchName("구로점")
						.build()
		);

		given(martService.findMartsById(new LinkedHashSet<>(asList(1L, 5L)), defaultSort()))
				.willReturn(response);

		ResultActions result = mockMvc.perform(
				get("/api/marts")
						.param("ids", "1,5")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].id").value(1))
				.andExpect(jsonPath("$.data[1].id").value(5))
				.andDo(print());
	}

	@Test
	public void 아이디로_마트_조회() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/api/marts/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].id").value(1))
				.andDo(print());
	}

	@Test
	public void 마트타입조회() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/api/marts/types")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void 마트타입으로_소문자로_리스트_요청() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/api/marts/types/{mart_type}", getFirstMartTypeName().toLowerCase())
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void 마트타입으로_대문자로_리스트_요청() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/api/marts/types/{mart_type}", getFirstMartTypeName().toUpperCase())
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void 잘못된_마트타입으로_리스트_요청() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/api/marts/types/{mart_type}", "hongmart")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		result.andExpect(status().isBadRequest())
				.andDo(print());
	}

	private String getFirstMartTypeName() {
		return MartType.values()[0].toString();
	}
}