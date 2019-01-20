package com.hongsi.martholidayalarm.mart.domain;

import com.hongsi.martholidayalarm.mart.dto.MartOrderRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class MartSortBuilder {

	public static Sort parseSort(List<MartOrderRequest> orderRequest, Order... defaultOrders) {
		if (defaultOrders == null) {
			throw new IllegalArgumentException("Must pass default sort");
		}
		if (orderRequest == null) {
			return Sort.by(defaultOrders);
		}
		List<Order> orders = orderRequest.stream()
				.map(MartOrderRequest::toMartOrder)
				.filter(MartOrder::isValid)
				.map(MartOrder::getOrder)
				.collect(Collectors.toList());
		return Sort.by(orders);
	}
}
