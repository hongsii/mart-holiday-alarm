package com.hongsi.martholidayalarm.api.dto.mart;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MartSortParser {

    public static Sort parse(List<String> orders, Order... defaultValue) {
        return orders != null ? parseOrders(orders) : getDefaultSort(defaultValue);
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
        return defaultValue != null ? Sort.by(defaultValue) : Sort.unsorted();
    }
}
