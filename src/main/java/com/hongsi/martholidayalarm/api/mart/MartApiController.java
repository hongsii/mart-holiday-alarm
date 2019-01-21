package com.hongsi.martholidayalarm.api.mart;

import com.hongsi.martholidayalarm.api.mart.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.domain.mart.MartOrder.Property;
import com.hongsi.martholidayalarm.domain.mart.MartSortBuilder;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.exception.MissingParameterException;
import com.hongsi.martholidayalarm.service.MartService;
import com.hongsi.martholidayalarm.service.dto.mart.LocationDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartOrderDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartTypeDto;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
			@RequestParam(name = "sort", required = false) List<MartOrderDto.Parameter> orderParameters) {
		Order[] defaultOrders = new Order[]{ Property.martType.asc(), Property.branchName.asc() };
		Sort sort = MartSortBuilder.parseSort(orderParameters, defaultOrders);
		List<MartDto.Response> marts = martService.findMarts(sort);
		return ResponseEntity.ok(marts);
	}

	@GetMapping(params = "ids")
	public ResponseEntity<?> getMartsByIds(
			@RequestParam(name = "ids") Set<Long> ids,
			@RequestParam(name = "sort", required = false) List<MartOrderDto.Parameter> orderParameters) {
		Sort sort = MartSortBuilder.parseSort(orderParameters, Property.id.asc());
		List<MartDto.Response> marts = martService.findMartsById(ids, sort);
		return ResponseEntity.ok(marts);
	}

	@GetMapping(params = {"latitude", "longitude"})
	public ResponseEntity<?> getMartsByLocation(@Valid @ModelAttribute LocationDto.Request request) {
		List<MartDto.Response> marts = martService.findMartsByLocation(request);
		return ResponseEntity.ok(marts);
	}

	@GetMapping(params = "latitude")
	public void getMartsByLocationMissingLongitude() {
		throw new MissingParameterException("longitude parameter required");
	}

	@GetMapping(params = "longtitude")
	public void errorMartsByLocationMissingLatitude() {
		throw new MissingParameterException("latitude parameter required");
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getMartsById(@PathVariable Long id) {
		MartDto.Response mart = martService.findMartById(id);
		return ResponseEntity.ok(mart);
	}

	@GetMapping(value = "/types")
	public ResponseEntity<?> getMartTypes() {
		List<MartTypeDto.Response> martTypes = martService.findMartTypes();
		return ResponseEntity.ok(martTypes);
	}

	@GetMapping(value = "/types/{martType}")
	public ResponseEntity<?> getMartsByMartType(
			@PathVariable @Valid MartType martType,
			@RequestParam(name = "sort", required = false) List<MartOrderDto.Parameter> orderParameters) {
		Sort sort = MartSortBuilder.parseSort(orderParameters, Property.branchName.asc());
		List<MartDto.Response> marts = martService.findMartsByMartType(martType, sort);
		return ResponseEntity.ok(marts);
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
	}
}

