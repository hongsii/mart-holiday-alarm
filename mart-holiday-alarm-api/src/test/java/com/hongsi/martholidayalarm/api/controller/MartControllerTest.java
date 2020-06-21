package com.hongsi.martholidayalarm.api.controller;

import com.hongsi.martholidayalarm.api.docs.CommonApiDocumentConfigure;
import com.hongsi.martholidayalarm.api.dto.mart.LocationParameter;
import com.hongsi.martholidayalarm.api.dto.mart.MartDto;
import com.hongsi.martholidayalarm.api.dto.mart.MartTypeDto;
import com.hongsi.martholidayalarm.api.service.MartService;
import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.location.Location;
import com.hongsi.martholidayalarm.core.mart.Mart;
import com.hongsi.martholidayalarm.core.mart.MartType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.*;

import static com.hongsi.martholidayalarm.api.controller.MartDocumentDescriptor.getMartFieldDescriptors;
import static com.hongsi.martholidayalarm.api.controller.MartDocumentDescriptor.getSortParameterDescriptor;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MartController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class MartControllerTest extends CommonApiDocumentConfigure {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MartService martService;

    @Test
    public void 전체_마트_조회() throws Exception {
        given(martService.findAll(any())).willReturn(RESPONSE);

        ResultActions result = mockMvc.perform(
                get("/api/marts")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("marts-find-all",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                getSortParameterDescriptor("martType:asc, branchName:asc")
                        ),
                        apiResponseFieldSnippet(true, getMartFieldDescriptors())
                ));
    }

    @Test
    public void 아이디로_다수_마트_조회() throws Exception {
        given(martService.findAllById(new LinkedHashSet<>(asList(1L, 5L)), Sort.by("id"))).willReturn(RESPONSE);

        ResultActions result = mockMvc.perform(
                get("/api/marts?ids=1,5")
                        .contentType(MediaType.APPLICATION_JSON)
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
                        apiResponseFieldSnippet(true, getMartFieldDescriptors())
                ));
    }

    @Test
    public void 좌표로_마트_조회() throws Exception {
        List<MartDto> response = Collections.singletonList(
                new MartDto(
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
                                .location(Location.of(37.47915561, 127.11836061))
                                .build(),
                        2.1302443883659024
                )
        );

        given(martService.findAllByLocation(any(LocationParameter.class))).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/api/marts?latitude=37.460000&longitude=127.118000&distance=3")
                        .contentType(MediaType.APPLICATION_JSON)
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
                        apiResponseFieldSnippet(true, getMartFieldDescriptors())
                                .and(fieldWithPath("location.distance").type(JsonFieldType.NUMBER).description("조회한 위치로부터 거리 (km)"))
                ));
    }

    @Test
    public void 아이디로_마트_조회() throws Exception {
        given(martService.findById(1L)).willReturn(
                new MartDto(Mart.builder()
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
                        .build())
        );

        ResultActions result = mockMvc.perform(
                get("/api/marts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("marts-find-one",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(parameterWithName("id").description("아이디")),
                        apiResponseFieldSnippet(false, getMartFieldDescriptors())
                ));
    }

    @Test
    public void 마트타입조회() throws Exception {
        given(martService.findAllMartTypes()).willReturn(
                Arrays.stream(MartType.values())
                        .map(MartTypeDto::new)
                        .collect(toList())
        );

        ResultActions result = mockMvc.perform(
                get("/api/marts/types")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("marts-types-find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        apiResponseFieldSnippet(true,
                                fieldWithPath("value").type(JsonFieldType.STRING).description("마트타입 값"),
                                fieldWithPath("displayName").type(JsonFieldType.STRING).description("마트타입명")
                        )
                ));
    }

    @Test
    public void 마트타입으로_리스트_요청() throws Exception {
        given(martService.findAllByMartType(any(MartType.class), any(Sort.class)))
                .willReturn(RESPONSE);

        ResultActions result = mockMvc.perform(
                get("/api/marts/types/{martType}", getFirstMartTypeName().toLowerCase())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("marts-find-by-type",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(parameterWithName("martType").description("마트타입 ex) emart, lottemart ...")),
                        requestParameters(getSortParameterDescriptor("branchName:asc")),
                        apiResponseFieldSnippet(true, getMartFieldDescriptors())
                ));
    }

    @Test
    public void 잘못된_마트타입으로_리스트_요청() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/marts/types/{martType}", "hongmart")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest())
                .andDo(print());
    }

    private String getFirstMartTypeName() {
        return MartType.values()[0].toString();
    }

    private static final List<MartDto> RESPONSE = asList(
            new MartDto(
                    Mart.builder()
                            .id(1L)
                            .martType(MartType.EMART)
                            .branchName("가든5점")
                            .region("서울")
                            .phoneNumber("02-411-1234")
                            .address("서울특별시 송파구 충민로 10")
                            .openingHours("10:00~23:00")
                            .url("http://store.emart.com/branch/list.do?id=1140")
                            .holidays(new TreeSet<>(asList(Holiday.of(LocalDate.now()), Holiday.of(LocalDate.now().plusDays(1)))))
                            .location(Location.of(37.47915561, 127.11836061))
                            .build()
            ),
            new MartDto(
                    Mart.builder()
                            .id(5L)
                            .martType(MartType.EMART)
                            .branchName("검단점")
                            .region("인천")
                            .phoneNumber("032-440-1234")
                            .address("인천광역시 서구 서곶로 754")
                            .openingHours("10:00~23:00")
                            .url("http://store.emart.com/branch/list.do?id=1101")
                            .holidays(new TreeSet<>(asList(Holiday.of(2019, 1, 1), Holiday.of(2019, 1, 11))))
                            .location(Location.of(37.07915561, 126.11836061))
                            .build()
            )
    );
}