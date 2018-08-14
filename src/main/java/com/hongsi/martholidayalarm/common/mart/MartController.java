package com.hongsi.martholidayalarm.common.mart;

import com.hongsi.martholidayalarm.common.mart.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.dto.MartDto;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mart")
@AllArgsConstructor
@Api(description = "마트 정보 API", tags = {"마트"})
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request - invalid parameters")
})
public class MartController {

	private final MartService martService;

	@ApiOperation(value = "특정 마트의 지점 조회")
	@ApiImplicitParam(name = "mart_type", value = "마트타입 (대소문자 구분하지 않음)", required = true, dataType = "string", paramType = "path")
	@GetMapping(value = "/{mart_type}/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MartDto>> getMarts(
			@PathVariable("mart_type") @Valid MartType martType) {
		List<MartDto> marts = martService.getMartsByMartType(martType);
		return new ResponseEntity<>(marts, HttpStatus.OK);
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This request has invalid parameters, check parameters")
	public ResponseEntity<String> handleBadRequest(IllegalArgumentException exception) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
