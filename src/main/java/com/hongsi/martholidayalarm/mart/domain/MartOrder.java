package com.hongsi.martholidayalarm.mart.domain;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

@EqualsAndHashCode
@ToString
public class MartOrder {

	public static final String SORT_DELIMITER = ":";
	private static final Pattern SORT_PATTERN = Pattern.compile("([a-zA-Z]+)(:(asc|desc))?");
	private static final int PROPERTY_INDEX = 1;
	private static final int DIRECTION_INDEX = 3;
	private static final MartOrder EMPTY_ORDER = new MartOrder(Optional.empty());

	private Optional<Order> order;

	public MartOrder(Optional<Order> order) {
		this.order = order;
	}

	public static MartOrder from(String orderValue) {
		try {
			Matcher matcher = SORT_PATTERN.matcher(orderValue);
			if (!matcher.matches()) {
				throw new IllegalArgumentException("Not found sort value");
			}

			String property = matcher.group(PROPERTY_INDEX);
			Mart.class.getDeclaredField(property); // exists property or not

			String direction = matcher.group(DIRECTION_INDEX);
			if (Objects.isNull(direction)) {
				return new MartOrder(Optional.of(Order.asc(property)));
			}
			return new MartOrder(Optional.of(new Order(Direction.fromString(direction), property)));
		} catch (Exception e) {
			return empty();
		}
	}

	public static MartOrder empty() {
		return EMPTY_ORDER;
	}

	public boolean isValid() {
		return order.isPresent();
	}

	public Order getOrder() {
		if (!isValid()) {
			throw new IllegalStateException("Not exists Order");
		}
		return order.get();
	}
}
