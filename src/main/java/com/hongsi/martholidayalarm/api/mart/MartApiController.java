package com.hongsi.martholidayalarm.api.mart;

import com.hongsi.martholidayalarm.api.mart.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.mart.domain.MartOrder.Property;
import com.hongsi.martholidayalarm.mart.domain.MartSortBuilder;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.MartOrderRequest;
import com.hongsi.martholidayalarm.mart.dto.MartResponse;
import com.hongsi.martholidayalarm.mart.dto.MartTypeResponse;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/marts")
@RequiredArgsConstructor
public class MartApiController {

	private final MartService martService;

	@GetMapping
	public ResponseEntity<?> getMarts(
			@RequestParam(name = "sort", required = false) List<MartOrderRequest> martOrderRequest) {
		Order[] defaultOrders = new Order[]{ Property.martType.asc(), Property.branchName.asc() };
		Sort sort = MartSortBuilder.parseSort(martOrderRequest, defaultOrders);
		List<MartResponse> marts = martService.findMarts(sort);
		return ResponseEntity.ok(marts);
	}

	@GetMapping(params = "ids")
	public ResponseEntity<?> getMartsByIds(
			@RequestParam(name = "ids") Set<Long> ids,
			@RequestParam(name = "sort", required = false) List<MartOrderRequest> martOrderRequest) {
		Sort sort = MartSortBuilder.parseSort(martOrderRequest, Property.id.asc());
		List<MartResponse> marts = martService.findMartsById(ids, sort);
		return ResponseEntity.ok(marts);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getMartsById(@PathVariable Long id) {
		MartResponse mart = martService.findMartById(id);
		return ResponseEntity.ok(mart);
	}

	@GetMapping(value = "/types")
	public ResponseEntity<?> getMartTypes() {
		List<MartTypeResponse> martTypes = martService.findMartTypes();
		return ResponseEntity.ok(martTypes);
	}

	@GetMapping(value = "/types/{martType}")
	public ResponseEntity<?> getMartsByMartType(
			@PathVariable @Valid MartType martType,
			@RequestParam(name = "sort", required = false) List<MartOrderRequest> martOrderRequest) {
		Sort sort = MartSortBuilder.parseSort(martOrderRequest, Property.branchName.asc());
		List<MartResponse> marts = martService.findMartsByMartType(martType, sort);
		return ResponseEntity.ok(marts);
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
	}
}

