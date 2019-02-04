package com.hongsi.martholidayalarm.api.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

import java.util.ArrayList;
import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

public interface MartApiDocumentDescriptor {

	static List<FieldDescriptor> getMartFieldDescriptors(boolean isArray) {
		List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
		String path = "";
		if (isArray) {
			String arrayPath = "[]";
			path = arrayPath + ".";
			fieldDescriptors.add(fieldWithPath(arrayPath).type(JsonFieldType.ARRAY).description("마트 리스트"));
		}
		fieldDescriptors.add(fieldWithPath(path + "id").type(JsonFieldType.NUMBER).description("아이디"));
		fieldDescriptors.add(fieldWithPath(path + "createdDate").type(JsonFieldType.STRING).description("생성일시"));
		fieldDescriptors.add(fieldWithPath(path + "modifiedDate").type(JsonFieldType.STRING).description("수정일시"));
		fieldDescriptors.add(fieldWithPath(path + "martType").type(JsonFieldType.STRING).description("마트타입"));
		fieldDescriptors.add(fieldWithPath(path + "branchName").type(JsonFieldType.STRING).description("지점명"));
		fieldDescriptors.add(fieldWithPath(path + "region").type(JsonFieldType.STRING).description("지역"));
		fieldDescriptors.add(fieldWithPath(path + "phoneNumber").type(JsonFieldType.STRING).description("전화번호"));
		fieldDescriptors.add(fieldWithPath(path + "address").type(JsonFieldType.STRING).description("주소"));
		fieldDescriptors.add(fieldWithPath(path + "openingHours").type(JsonFieldType.STRING).description("영업시간"));
		fieldDescriptors.add(fieldWithPath(path + "url").type(JsonFieldType.STRING).description("홈페이지"));
		fieldDescriptors.add(fieldWithPath(path + "holidays[]").type(JsonFieldType.ARRAY).description("휴일"));
		fieldDescriptors.add(fieldWithPath(path + "location").type(JsonFieldType.OBJECT).description("좌표"));
		fieldDescriptors.add(fieldWithPath(path + "location.latitude").type(JsonFieldType.NUMBER).description("위도"));
		fieldDescriptors.add(fieldWithPath(path + "location.longitude").type(JsonFieldType.NUMBER).description("경도"));
		return fieldDescriptors;
	}

	static ParameterDescriptor getSortParameterDescriptor(String defaultSort) {
		return parameterWithName("sort").description("정렬 방법 - 정렬 방향을 생략하면 ASC로 적용 (다건의 경우 콤마(,)로 구분)").optional()
				.attributes(key("format").value("필드명[:ASC|DESC]"))
				.attributes(key("default").value(defaultSort));
	}
}
