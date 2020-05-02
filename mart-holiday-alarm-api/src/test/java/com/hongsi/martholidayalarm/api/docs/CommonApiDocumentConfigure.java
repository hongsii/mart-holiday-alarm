package com.hongsi.martholidayalarm.api.docs;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

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

    protected ResponseFieldsSnippet apiResponseFieldSnippet(boolean isArray, FieldDescriptor... fieldDescriptors) {
        return apiResponseFieldSnippet(isArray, asList(fieldDescriptors));
    }

    protected ResponseFieldsSnippet apiResponseFieldSnippet(boolean isArray, List<FieldDescriptor> fieldDescriptors) {
        String path = "data";
        if (isArray) {
            path += "[]";
        }
        return responseFields(
                beneathPath(path).withSubsectionId("data"),
                fieldDescriptors
        );
    }
}
