package com.hongsi.martholidayalarm.domain.mart;

import static com.hongsi.martholidayalarm.domain.mart.MartSortBuilder.parseSort;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.domain.mart.MartOrder.Property;
import com.hongsi.martholidayalarm.service.dto.mart.MartOrderDto.Parameter;
import org.junit.Test;
import org.springframework.data.domain.Sort;

public class MartSortBuilderTest {

	@Test
	public void 요청으로_Sort_파싱() {
		Sort sort = parseSort(asList(
				Parameter.of("martType:asc"),
				Parameter.of("urlqqq")
		), MartOrder.Property.id.asc());

		assertThat(sort).containsOnly(Property.martType.asc());
	}
}