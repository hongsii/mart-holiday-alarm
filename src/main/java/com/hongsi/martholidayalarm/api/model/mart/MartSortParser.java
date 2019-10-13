package com.hongsi.martholidayalarm.api.model.mart;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MartSortParser {

	public static Sort parse(List<String> orders, Order... defaultValue) {
		return Optional.ofNullable(orders)
				.map(MartSortParser::parseOrders)
				.orElse(getDefaultSort(defaultValue));
	}

	private static Sort parseOrders(List<String> orders) {
		return Sort.by(
				orders.stream()
						.map(MartOrderParser::parse)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
						.collect(Collectors.toList())
		);
	}

	private static Sort getDefaultSort(Order[] defaultValue) {
		return Optional.ofNullable(defaultValue)
				.map(Sort::by)
				.orElseGet(Sort::unsorted);
	}
}
