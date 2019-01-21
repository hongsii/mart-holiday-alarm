package com.hongsi.martholidayalarm.domain.mart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MartTypeTest {

	@Test
	public void 이름으로_마트타입_찾기() {
		assertThat(MartType.of("홈플러스익스프레스")).isEqualTo(MartType.HOMEPLUS_EXPRESS);
	}
}