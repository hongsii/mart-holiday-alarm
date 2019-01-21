package com.hongsi.martholidayalarm.service.dto.mart;

import com.hongsi.martholidayalarm.domain.mart.MartOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MartOrderDto {

	@Getter
	@Setter
	public static class Parameter {

		private String orderValue;

		private Parameter(String orderValue) {
			this.orderValue = orderValue;
		}

		public static Parameter of(String orderValue) {
			return new Parameter(orderValue);
		}

		public MartOrder toEntity() {
			return MartOrder.from(orderValue);
		}
	}
}
