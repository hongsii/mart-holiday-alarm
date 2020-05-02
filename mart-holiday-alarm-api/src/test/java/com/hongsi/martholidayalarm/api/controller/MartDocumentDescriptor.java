package com.hongsi.martholidayalarm.api.controller;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public interface MartDocumentDescriptor {

    static List<FieldDescriptor> getMartFieldDescriptors() {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.add(fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"));
        fieldDescriptors.add(fieldWithPath("createdDate").type(JsonFieldType.STRING).description("생성일시"));
        fieldDescriptors.add(fieldWithPath("modifiedDate").type(JsonFieldType.STRING).description("수정일시"));
        fieldDescriptors.add(fieldWithPath("martType").type(JsonFieldType.STRING).description("마트타입"));
        fieldDescriptors.add(fieldWithPath("branchName").type(JsonFieldType.STRING).description("지점명"));
        fieldDescriptors.add(fieldWithPath("region").type(JsonFieldType.STRING).description("지역"));
        fieldDescriptors.add(fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화번호"));
        fieldDescriptors.add(fieldWithPath("address").type(JsonFieldType.STRING).description("주소"));
        fieldDescriptors.add(fieldWithPath("openingHours").type(JsonFieldType.STRING).description("영업시간"));
        fieldDescriptors.add(fieldWithPath("url").type(JsonFieldType.STRING).description("홈페이지"));
        fieldDescriptors.add(fieldWithPath("holidays[]").type(JsonFieldType.ARRAY).description("휴일"));
        fieldDescriptors.add(fieldWithPath("location").type(JsonFieldType.OBJECT).description("좌표"));
        fieldDescriptors.add(fieldWithPath("location.latitude").type(JsonFieldType.NUMBER).description("위도"));
        fieldDescriptors.add(fieldWithPath("location.longitude").type(JsonFieldType.NUMBER).description("경도"));
        return fieldDescriptors;
    }

    static ParameterDescriptor getSortParameterDescriptor(String defaultSort) {
        return parameterWithName("sort").description("정렬 방법 - 정렬 방향을 생략하면 ASC로 적용 (다건의 경우 콤마(,)로 구분)").optional()
                .attributes(key("format").value("필드명[:ASC|DESC]"))
                .attributes(key("default").value(defaultSort));
    }
}
