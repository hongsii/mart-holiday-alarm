package com.hongsi.martholidayalarm.mart.domain;

import static com.hongsi.martholidayalarm.mart.domain.MartSortBuilder.parseSort;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.mart.domain.MartOrder.Property;
import com.hongsi.martholidayalarm.mart.dto.MartOrderRequest;
import org.junit.Test;
import org.springframework.data.domain.Sort;

public class MartSortBuilderTest {

	@Test
	public void 요청으로_Sort_파싱() {
		Sort sort = parseSort(asList(
				new MartOrderRequest("martType:asc"),
				new MartOrderRequest("urlqqq")
		), MartOrder.Property.id.asc());

		assertThat(sort).containsOnly(Property.martType.asc());
	}
}