package com.hongsi.martholidayalarm.api.mart.controller;

import com.hongsi.martholidayalarm.api.mart.dto.MartResponseDto;
import com.hongsi.martholidayalarm.api.mart.service.BranchApiService;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BranchApiController {

	private final BranchApiService branchApiService;

	@GetMapping(value = "/api/branch/{mart_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<MartResponseDto> getBranches(@PathVariable("mart_id") @Valid Long id)
			throws Exception {
		MartResponseDto mart = branchApiService.getMartById(id);
		return new ResponseEntity<>(mart, HttpStatus.OK);
	}

	@GetMapping(value = "/api/branches/{mart_ids}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<MartResponseDto>> getBranches(
			@PathVariable("mart_ids") @Valid Set<Long> ids) throws Exception {
		List<MartResponseDto> marts = branchApiService.getMartsById(ids);
		return new ResponseEntity<>(marts, HttpStatus.OK);
	}
}
