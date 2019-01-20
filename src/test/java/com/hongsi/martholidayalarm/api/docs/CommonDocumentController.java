package com.hongsi.martholidayalarm.api.docs;

import com.hongsi.martholidayalarm.api.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonDocumentController {

	@GetMapping("/docs")
	public ApiResponse<?> docsHome() {
		return new ApiResponse<>("response wrapper");
	}
}
