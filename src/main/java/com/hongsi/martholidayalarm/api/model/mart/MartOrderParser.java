package com.hongsi.martholidayalarm.api.model.mart;

import org.springframework.data.domain.Sort.Order;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MartOrderParser {

    private static final Pattern ORDER_PATTERN = Pattern.compile("([a-zA-Z]+):?(asc|desc)?");
    private static final int PROPERTY_GROUP = 1;
    private static final int DIRECTION_GROUP = 2;

    public static Optional<Order> parse(String value) {
        try {
            Matcher matcher = ORDER_PATTERN.matcher(value);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Not found order value");
            }

            MartOrder martOrder = MartOrder.of(matcher.group(PROPERTY_GROUP));
            Order order = Optional.ofNullable(matcher.group(DIRECTION_GROUP))
                    .map(martOrder::by)
                    .orElseGet(martOrder::asc);
            return Optional.of(order);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
