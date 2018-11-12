package com.hongsi.martholidayalarm.api.mart.controller;

import com.hongsi.martholidayalarm.api.mart.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.api.mart.dto.MartResponseDto;
import com.hongsi.martholidayalarm.api.mart.service.MartApiService;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MartApiController {

	private final MartApiService martApiService;

	@GetMapping(value = "/api/marts/{mart_type}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<MartResponseDto>> getMarts(@PathVariable("mart_type") @Valid MartType martType) {
		List<MartResponseDto> marts = martApiService.getMartsByMartType(martType);
		return new ResponseEntity<>(marts, HttpStatus.OK);
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
	}
}

