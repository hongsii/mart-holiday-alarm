package com.hongsi.martholidayalarm.api.mart;

import static com.hongsi.martholidayalarm.api.mart.MartApiDocumentDescriptor.getMartFieldDescriptors;
import static com.hongsi.martholidayalarm.api.mart.MartApiDocumentDescriptor.getSortParameterDescriptor;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hongsi.martholidayalarm.api.docs.CommonApiDocumentConfigure;
import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartTest;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.service.MartService;
import com.hongsi.martholidayalarm.service.dto.mart.LocationDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartDto.Response;
import com.hongsi.martholidayalarm.service.dto.mart.MartTypeDto;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(MartApiController.class)
public class MartApiControllerTest extends CommonApiDocumentConfigure {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MartService martService;

	@Test
	public void 전체_마트_조회() throws Exception {
		List<MartDto.Response> response = MartTest.newMartsResponse;
		given(martService.findMarts(any())).willReturn(response);

		ResultActions result = mockMvc.perform(
				get("/api/marts")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(document("marts-find-all",
						getDocumentRequest(),
						getDocumentResponse(),
						requestParameters(
								getSortParameterDescriptor("martType:asc, branchName:asc")
						),
						apiResponseFieldSnippet(getMartFieldDescriptors(true))
				));
	}

	@Test
	public void 아이디로_다수_마트_조회() throws Exception {
		List<MartDto.Response> response = MartTest.newMartsResponse;
		given(martService.findMartsById(new LinkedHashSet<>(asList(1L, 5L)), Sort.by("id")))
				.willReturn(response);

		ResultActions result = mockMvc.perform(
				get("/api/marts?ids=1,5")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].id").value(1))
				.andExpect(jsonPath("$.data[1].id").value(5))
				.andDo(document("marts-find-by-ids",
						getDocumentRequest(),
						getDocumentResponse(),
						requestParameters(
								parameterWithName("ids").description("아이디 (다건의 경우 콤마(,)로 구분)"),
								getSortParameterDescriptor("id:asc")
						),
						apiResponseFieldSnippet(getMartFieldDescriptors(true))
				));
	}

	@Test
	public void 좌표로_마트_조회() throws Exception {
		List<Response> response = asList(
				Response.of(
						Mart.builder()
								.id(1L)
								.martType(MartType.EMART)
								.branchName("가든5점")
								.region("서울")
								.phoneNumber("02-411-1234")
								.address("서울특별시 송파구 충민로 10")
								.openingHours("10:00~23:00")
								.url("http://store.emart.com/branch/list.do?id=1140")
								.holidays(new TreeSet<>(asList(
										Holiday.of(2019, 1, 1), Holiday.of(2019, 1, 11))))
								.location(Location.of(37.47915561, 127.11836061, 2.1302443883659024))
								.build()
				)
		);

		given(martService.findMartsByLocation(any(LocationDto.Request.class))).willReturn(response);
		ResultActions result = mockMvc.perform(
				get("/api/marts?latitude=37.460000&longitude=127.118000&distance=3")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(document("marts-find-by-location",
						getDocumentRequest(),
						getDocumentResponse(),
						requestParameters(
								parameterWithName("latitude").description("위도")
										.attributes(key("format").value("-90 <= 위도 <= 90")),
								parameterWithName("longitude").description("경도")
										.attributes(key("format").value("-180 <= 경도 <= 180")),
								parameterWithName("distance").description("마트 거리 범위 (단위 : km)").optional()
										.attributes(key("default").value("3"))
						),
						apiResponseFieldSnippet(getMartFieldDescriptors(true))
								.and(fieldWithPath("[].location.distance").type(JsonFieldType.NUMBER).description("조회한 위치로부터 거리 (km)"))
				));
	}

	@Test
	public void 아이디로_마트_조회() throws Exception {
		MartDto.Response response = MartDto.Response.of(
				Mart.builder()
						.id(1L)
						.martType(MartType.EMART)
						.branchName("가든5점")
						.region("서울")
						.phoneNumber("02-411-1234")
						.address("서울특별시 송파구 충민로 10")
						.openingHours("10:00~23:00")
						.url("http://store.emart.com/branch/list.do?id=1140")
						.holidays(new TreeSet<>(asList(Holiday.of(2019, 1, 1), Holiday.of(2019, 1, 11))))
						.location(Location.of(37.47915561, 127.11836061))
						.build()
		);

		when(martService.findMartById(1L)).thenReturn(response);
		ResultActions result = mockMvc.perform(
				get("/api/marts/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(document("marts-find-one",
						getDocumentRequest(),
						getDocumentResponse(),
						pathParameters(parameterWithName("id").description("아이디")),
						apiResponseFieldSnippet(getMartFieldDescriptors(false))
				));
	}

	@Test
	public void 마트타입조회() throws Exception {
		List<MartTypeDto.Response> response = Arrays.stream(MartType.values())
				.map(MartTypeDto.Response::of)
				.collect(toList());
		given(martService.findMartTypes()).willReturn(response);

		ResultActions result = mockMvc.perform(
				get("/api/marts/types")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(document("marts-types-find",
						getDocumentRequest(),
						getDocumentResponse(),
						apiResponseFieldSnippet(
								fieldWithPath("[]").type(JsonFieldType.ARRAY).description("마트타입 리스트"),
								fieldWithPath("[].value").type(JsonFieldType.STRING).description("마트타입 값"),
								fieldWithPath("[].displayName").type(JsonFieldType.STRING).description("마트타입명")
						)
				));
	}

	@Test
	public void 마트타입으로_리스트_요청() throws Exception {
		List<MartDto.Response> response = MartTest.newMartsResponse;
		given(martService.findMartsByMartType(any(MartType.class), any(Sort.class)))
				.willReturn(response);

		ResultActions result = mockMvc.perform(
				get("/api/marts/types/{martType}", getFirstMartTypeName().toLowerCase())
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		);

		result.andExpect(status().isOk())
				.andDo(document("marts-find-by-type",
						getDocumentRequest(),
						getDocumentResponse(),
						pathParameters(parameterWithName("martType").description("마트타입 ex) emart, lottemart ...")),
						requestParameters(getSortParameterDescriptor("branchName:asc")),
						apiResponseFieldSnippet(getMartFieldDescriptors(true))
				));
	}

	@Test
	public void 잘못된_마트타입으로_리스트_요청() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/api/marts/types/{martType}", "hongmart")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		result.andExpect(status().isBadRequest())
				.andDo(print());
	}

	private String getFirstMartTypeName() {
		return MartType.values()[0].toString();
	}
}