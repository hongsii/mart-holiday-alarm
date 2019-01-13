package com.hongsi.martholidayalarm.api.mart;

import com.hongsi.martholidayalarm.api.mart.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.MartResponse;
import com.hongsi.martholidayalarm.mart.service.MartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
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
	public ResponseEntity<?> getMarts(
			@PathVariable("mart_type") @Valid MartType martType) {
		List<MartResponse> marts = martService.findMartsByMartType(martType);
		if (martType == MartType.EMART) {
			marts.addAll(martService.findMartsByMartType(MartType.EMART_TRADERS));
		}
		return new ResponseEntity<>(marts.stream()
				.map(MartResponse::toTempResponse)
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@ApiOperation(value = "지점ID로 조회")
	@ApiImplicitParam(name = "ids", value = "지점ID (한 개 또는 그 이상)", required = true, paramType = "path", allowMultiple = true)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "There is no exist mart for id")
	})
	@GetMapping(value = "/branch/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBranches(
			@PathVariable("ids") @Valid Set<Long> ids) {
		List<MartResponse> marts = martService.findMartsById(ids);
		return new ResponseEntity<>(marts.stream()
				.map(MartResponse::toTempResponse)
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
	}
}
