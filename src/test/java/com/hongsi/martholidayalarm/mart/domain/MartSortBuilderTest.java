package com.hongsi.martholidayalarm.mart.domain;

import static com.hongsi.martholidayalarm.mart.domain.MartSortBuilder.parseSort;
import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.mart.dto.MartOrderRequest;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class MartSortBuilderTest {

	@Test
	public void 요청으로_Sort_파싱() {
		Sort sort = parseSort(
				new MartOrderRequest("martType:asc"),
				new MartOrderRequest("urlqqq")
		);

		assertThat(sort).containsOnly(Order.asc("martType"));
	}
}