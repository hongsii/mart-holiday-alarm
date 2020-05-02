package com.hongsi.martholidayalarm.api.docs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommonDocumentController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CommonDocumentControllerTest extends CommonApiDocumentConfigure {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void commonResponse() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/docs")
						.contentType(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
				.andDo(document("common",
						commonResponseFields("common-response", null,
								attributes(key("title").value("공통 응답")),
								subsectionWithPath("code").description("응답 코드"),
								subsectionWithPath("message").description("응답 메세지"),
								subsectionWithPath("data").description("데이터")
						)
				));
	}

	public static CommonResponseFieldsSnippet commonResponseFields(
			String type, PayloadSubsectionExtractor<?> subsectionExtractor,
			Map<String, Object> attributes, FieldDescriptor... descriptors) {
		return new CommonResponseFieldsSnippet(type,subsectionExtractor,
				Arrays.asList(descriptors), attributes, true);
	}
}
