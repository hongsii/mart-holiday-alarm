package com.hongsi.martholidayalarm.domain.mart;

import com.hongsi.martholidayalarm.service.dto.mart.MartOrderDto.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class MartSortBuilder {

	public static Sort parseSort(List<Parameter> orderParameters, Order... defaultOrders) {
		if (defaultOrders == null) {
			throw new IllegalArgumentException("Must pass default sort");
		}
		if (orderParameters == null) {
			return Sort.by(defaultOrders);
		}
		List<Order> orders = orderParameters.stream()
				.map(Parameter::toEntity)
				.filter(MartOrder::isValid)
				.map(MartOrder::getOrder)
				.collect(Collectors.toList());
		return Sort.by(orders);
	}
}
