package com.hongsi.martholidayalarm.api.mart;

import com.hongsi.martholidayalarm.api.mart.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.MartResponse;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/marts")
@RequiredArgsConstructor
public class MartApiController {

	private final MartService martService;

	@GetMapping(value = "/{ids}")
	public ResponseEntity<List<MartResponse>> getMartsById(@PathVariable("ids") Set<Long> ids) {
		List<MartResponse> marts = martService.findMartsById(ids);
		return ResponseEntity.ok(marts);
	}

	@GetMapping(value = "/types")
	public ResponseEntity<List<String>> getMartTypeNames() {
		List<String> names = martService.findMartTypeNames();
		return ResponseEntity.ok(names);
	}

	@GetMapping(value = "/types/{mart_type}")
	public ResponseEntity<List<MartResponse>> getMartsByMartType(
			@PathVariable("mart_type") @Valid MartType martType) {
		List<MartResponse> marts = martService.findMartsByMartType(martType);
		return ResponseEntity.ok(marts);
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
	}
}

