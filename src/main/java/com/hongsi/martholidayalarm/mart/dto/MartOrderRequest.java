package com.hongsi.martholidayalarm.mart.dto;

import com.hongsi.martholidayalarm.common.utils.ValidationUtils;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import java.util.Optional;
import lombok.Data;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

@Data
public class MartOrderRequest {

	private static final String SORT_DELIMITER = ":";
	private String sort;

	public MartOrderRequest(String sort) {
		this.sort = sort;
	}

	public Optional<Order> toOrder() {
		if (!ValidationUtils.isNotBlank(sort)) {
			return Optional.empty();
		}

		String[] rawSort = sort.split(SORT_DELIMITER);
		Class<Mart> clazz = Mart.class;
		String property = rawSort[0];
		try {
			clazz.getDeclaredField(property); // property check
			if (rawSort.length >= 2) {
				Direction direction = Direction.fromString(rawSort[1]);
				return Optional.of(new Order(direction, property));
			}
			return Optional.of(Order.asc(property));
		} catch (NoSuchFieldException e) {
			return Optional.empty();
		} catch (IllegalArgumentException e) {
			return Optional.of(Order.asc(property));
		}
	}
}
