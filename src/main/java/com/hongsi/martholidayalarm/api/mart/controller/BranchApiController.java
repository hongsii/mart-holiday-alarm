package com.hongsi.martholidayalarm.api.mart.controller;

import com.hongsi.martholidayalarm.api.mart.dto.MartResponseDto;
import com.hongsi.martholidayalarm.api.mart.service.BranchApiService;
import java.util.ArrayList;
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

	@GetMapping(value = "/api/branches/{mart_ids}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<MartResponseDto>> getBranches(@PathVariable("mart_ids") @Valid Set<Long> ids) {
		List<MartResponseDto> marts = branchApiService.getMartsById(new ArrayList<>(ids));
		if (marts.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(marts, HttpStatus.OK);
	}
}
