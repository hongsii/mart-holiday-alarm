package com.hongsi.martholidayalarm.api.mart;

import com.hongsi.martholidayalarm.api.mart.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.MartOrderRequest;
import com.hongsi.martholidayalarm.mart.dto.MartResponse;
import com.hongsi.martholidayalarm.mart.dto.MartTypeResponse;
import com.hongsi.martholidayalarm.mart.service.MartService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
			@RequestParam(name = "sort", required = false) MartOrderRequest... orderRequest) {
		List<Order> orders = parseOrders(orderRequest);
		List<MartResponse> marts = martService.findMarts(orders);
		return ResponseEntity.ok(marts);
	}

	private List<Order> parseOrders(MartOrderRequest... orderRequest) {
		if (orderRequest == null) {
			return Collections.emptyList();
		}
		return Arrays.stream(orderRequest)
				.map(MartOrderRequest::toOrder)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/{ids}")
	public ResponseEntity<?> getMartsById(@PathVariable("ids") Set<Long> ids) {
		List<MartResponse> marts = martService.findMartsById(ids);
		return ResponseEntity.ok(marts);
	}

	@GetMapping(value = "/types")
	public ResponseEntity<?> getMartTypes() {
		List<MartTypeResponse> martTypes = martService.findMartTypes();
		return ResponseEntity.ok(martTypes);
	}

	@GetMapping(value = "/types/{martType}")
	public ResponseEntity<?> getMartsByMartType(@PathVariable @Valid MartType martType) {
		List<MartResponse> marts = martService.findMartsByMartType(martType);
		return ResponseEntity.ok(marts);
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
	}
}

