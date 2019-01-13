package com.hongsi.martholidayalarm.mart.dto;

import com.hongsi.martholidayalarm.mart.domain.MartOrder;
import lombok.Data;

@Data
public class MartOrderRequest {

	private String orderValue;

	public MartOrderRequest(String orderValue) {
		this.orderValue = orderValue;
	}

	public MartOrder toMartOrder() {
		return MartOrder.from(orderValue);
	}
}
