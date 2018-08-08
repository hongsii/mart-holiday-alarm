package com.hongsi.martholidayalarm.common.mart;

import com.hongsi.martholidayalarm.common.mart.dto.MartDTO;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mart")
@AllArgsConstructor
public class MartController {

	private final MartService martService;

	@GetMapping(value = "/{mart_type}/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MartDTO>> getMarts(@PathVariable("mart_type") String martType) {
		List<MartDTO> marts = martService.findMartsByMartType(martType);
		return new ResponseEntity<>(marts, HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This request has invalid parameters, check parameters")
	public ResponseEntity<String> handleBadRequest(IllegalArgumentException exception) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
