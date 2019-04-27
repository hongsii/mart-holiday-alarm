package com.hongsi.martholidayalarm.api.controller;

import com.hongsi.martholidayalarm.api.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.api.response.ApiResponse;
import com.hongsi.martholidayalarm.domain.mart.MartOrder.Property;
import com.hongsi.martholidayalarm.domain.mart.MartSortBuilder;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.exception.MissingParameterException;
import com.hongsi.martholidayalarm.service.MartService;
import com.hongsi.martholidayalarm.service.dto.mart.LocationDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/marts")
@RequiredArgsConstructor
public class MartApiController {

	private final MartService martService;

	@GetMapping
	public ApiResponse<?> getMarts(
			@RequestParam(name = "sort", required = false) List<MartOrderDto.Parameter> orderParameters) {
		Order[] defaultOrders = new Order[]{ Property.martType.asc(), Property.branchName.asc() };
		Sort sort = MartSortBuilder.parseSort(orderParameters, defaultOrders);
		return ApiResponse.ok(martService.findAll(sort));
	}

	@GetMapping(params = "ids")
	public ApiResponse<?> getMartsByIds(
			@RequestParam(name = "ids") Set<Long> ids,
			@RequestParam(name = "sort", required = false) List<MartOrderDto.Parameter> orderParameters) {
		Sort sort = MartSortBuilder.parseSort(orderParameters, Property.id.asc());
		return ApiResponse.ok(martService.findAllById(ids, sort));
	}

	@GetMapping(params = {"latitude", "longitude"})
	public ApiResponse<?> getMartsByLocation(@Valid @ModelAttribute LocationDto.Request request) {
		return ApiResponse.ok(martService.findAllByLocation(request));
	}

	@GetMapping(params = "latitude")
	public void getMartsByLocationMissingLongitude() {
		throw new MissingParameterException("경도(longitude)가 필요합니다.");
	}

	@GetMapping(params = "longitude")
	public void getMartsByLocationMissingLatitude() {
		throw new MissingParameterException("위도(latitude)가 필요합니다.");
	}

	@GetMapping(value = "/{id}")
	public ApiResponse<?> getMartsById(@PathVariable Long id) {
		return ApiResponse.ok(martService.findById(id));
	}

	@GetMapping(value = "/types")
	public ApiResponse<?> getMartTypes() {
		return ApiResponse.ok(martService.findMartTypes());
	}

	@GetMapping(value = "/types/{martType}")
	public ApiResponse<?> getMartsByMartType(
			@PathVariable @Valid MartType martType,
			@RequestParam(name = "sort", required = false) List<MartOrderDto.Parameter> orderParameters) {
		Sort sort = MartSortBuilder.parseSort(orderParameters, Property.branchName.asc());
		List<MartDto.Response> marts = martService.findAllByMartType(martType, sort);
		return ApiResponse.ok(marts);
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
	}
}

