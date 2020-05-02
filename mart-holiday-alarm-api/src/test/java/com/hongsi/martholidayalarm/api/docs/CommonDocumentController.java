package com.hongsi.martholidayalarm.api.docs;

import com.hongsi.martholidayalarm.api.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CommonDocumentController {

	@GetMapping("/docs")
	public ApiResponse<?> docsHome() {
		return ApiResponse.ok("response wrapper");
	}

	@GetMapping("/docs/error")
	public void docsHome(@Valid Double value) {
	}
}
