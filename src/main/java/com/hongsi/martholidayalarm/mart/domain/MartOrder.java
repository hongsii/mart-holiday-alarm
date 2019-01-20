package com.hongsi.martholidayalarm.mart.domain;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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

			String propertyName = matcher.group(PROPERTY_INDEX);
			Property property = Property.of(propertyName);

			String direction = matcher.group(DIRECTION_INDEX);
			if (direction != null) {
				return of(property.by(direction));
			}
			return of(property.asc());
		} catch (Exception e) {
			return empty();
		}
	}

	public static MartOrder of(Order order) {
		return new MartOrder(Optional.ofNullable(order));
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

	@Getter
	public enum Property {

		id, martType, branchName, region;

		private static String COLUMN_ERROR_MESSAGE = String.format("일치하는 정렬 컬럼이 없습니다.\n- 가능한 정렬 : [%s]",
				Arrays.stream(values())
						.map(Property::name)
						.collect(Collectors.joining(", "))
		);

		public static Property of(String name) {
			return Arrays.stream(values())
					.filter(property -> property.name().equals(name))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException(COLUMN_ERROR_MESSAGE));
		}

		public Order asc() {
			return Order.asc(name());
		}

		public Order desc() {
			return Order.desc(name());
		}

		public Order by(String direction) {
			return new Order(Direction.fromString(direction), name());
		}
	}
}
