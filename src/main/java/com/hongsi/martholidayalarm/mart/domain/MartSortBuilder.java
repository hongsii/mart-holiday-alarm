package com.hongsi.martholidayalarm.mart.domain;

import com.hongsi.martholidayalarm.mart.dto.MartOrderRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class MartSortBuilder {

	public static Sort parseSort(MartOrderRequest... orderRequest) {
		if (orderRequest == null) {
			return defaultSort();
		}
		List<Order> orders = Arrays.stream(orderRequest)
				.map(MartOrderRequest::toMartOrder)
				.filter(MartOrder::isValid)
				.map(MartOrder::getOrder)
				.collect(Collectors.toList());
		return Sort.by(orders);
	}

	public static Sort defaultSort() {
		Order martType = Order.asc("martType");
		Order branchName = Order.asc("branchName");
		return Sort.by(martType, branchName);
	}
}
