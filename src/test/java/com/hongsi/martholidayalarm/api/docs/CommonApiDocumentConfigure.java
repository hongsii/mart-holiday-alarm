package com.hongsi.martholidayalarm.api.docs;

import static java.util.Arrays.asList;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import java.util.List;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
public class CommonApiDocumentConfigure {

	protected OperationRequestPreprocessor getDocumentRequest() {
		return preprocessRequest(
				modifyUris()
						.scheme("https")
						.host("docs.api.com")
						.removePort(),
				prettyPrint());
	}

	protected OperationResponsePreprocessor getDocumentResponse() {
		return preprocessResponse(prettyPrint());
	}

	protected ResponseFieldsSnippet apiResponseFieldSnippet(FieldDescriptor... fieldDescriptors) {
		return apiResponseFieldSnippet(asList(fieldDescriptors));
	}

	protected ResponseFieldsSnippet apiResponseFieldSnippet(List<FieldDescriptor> fieldDescriptors) {
		return responseFields(
				beneathPath("data").withSubsectionId("data"),
				fieldDescriptors
		);
	}
}
