package com.hongsi.martholidayalarm.api.controller;

import com.hongsi.martholidayalarm.api.controller.converter.MartTypeParameterConverter;
import com.hongsi.martholidayalarm.api.dto.ApiResponse;
import com.hongsi.martholidayalarm.api.dto.mart.LocationParameter;
import com.hongsi.martholidayalarm.api.dto.mart.MartOrder;
import com.hongsi.martholidayalarm.api.dto.mart.MartSortParser;
import com.hongsi.martholidayalarm.api.service.MartService;
import com.hongsi.martholidayalarm.core.mart.MartType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/marts")
@RequiredArgsConstructor
public class MartController {

    private final MartService martService;

    @GetMapping
    public ApiResponse<?> getMarts(@RequestParam(name = "sort", required = false) List<String> orders) {
        Sort sort = MartSortParser.parse(orders, MartOrder.martType.asc(), MartOrder.branchName.asc());
        return ApiResponse.ok(martService.findAll(sort));
    }

    @GetMapping(params = "ids")
    public ApiResponse<?> getMartsByIds(@RequestParam(name = "ids") Set<Long> ids,
                                        @RequestParam(name = "sort", required = false) List<String> orders) {
        Sort sort = MartSortParser.parse(orders, MartOrder.id.asc());
        return ApiResponse.ok(martService.findAllById(ids, sort));
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<?> getMartsById(@PathVariable Long id) {
        return ApiResponse.ok(martService.findById(id));
    }

    @GetMapping(value = "/types")
    public ApiResponse<?> getMartTypes() {
        return ApiResponse.ok(martService.findAllMartTypes());
    }

    @GetMapping(value = "/types/{martType}")
    public ApiResponse<?> getMartsByMartType(@PathVariable @Valid MartType martType,
                                             @RequestParam(name = "sort", required = false) List<String> orders) {
        Sort sort = MartSortParser.parse(orders, MartOrder.branchName.asc());
        return ApiResponse.ok(martService.findAllByMartType(martType, sort));
    }

    @GetMapping(params = {"latitude", "longitude"})
    public ApiResponse<?> getMartsByLocation(@Valid LocationParameter parameter) {
        return ApiResponse.ok(martService.findAllByLocation(parameter));
    }

    @GetMapping(params = "latitude")
    public void getMartsByLocationMissingLongitude() {
        throw new IllegalArgumentException("경도(longitude)가 필요합니다.");
    }

    @GetMapping(params = "longitude")
    public void getMartsByLocationMissingLatitude() {
        throw new IllegalArgumentException("위도(latitude)가 필요합니다.");
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(MartType.class, new MartTypeParameterConverter());
    }
}

