package com.hongsi.martholidayalarm.domain.mart;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.domain.mart.MartOrder.Property;
import org.junit.Test;
import org.springframework.data.domain.Sort.Order;

public class MartOrderTest {

	@Test
	public void 문자열에서_정렬_파싱() {
		final String property = "martType";
		assertThat(MartOrder.from(property))
				.isEqualTo(MartOrder.of(Order.asc(property)));
	}

	@Test
	public void 문자열에서_프로퍼티와_방향_파싱() {
		final String property = "martType";
		final String direction = "desc";
		assertThat(MartOrder.from(createOrderValue(property, direction)))
				.isEqualTo(MartOrder.of(Order.desc(property)));
	}

	@Test
	public void 잘못된_문자열_파싱() {
		assertThat(asList(
				MartOrder.from("martFoo"),
				MartOrder.from("martType:ascFoo"))
		).containsOnly(MartOrder.empty());
	}

	@Test
	public void 유효한_정렬_확인() {
		MartOrder martOrder = MartOrder.from("martType");
		assertThat(martOrder.isValid()).isTrue();
	}

	@Test
	public void 유효하지않은_정렬_확인() {
		MartOrder martOrder = MartOrder.from("foofoo");
		assertThat(martOrder.isValid()).isFalse();
	}

	@Test(expected = IllegalStateException.class)
	public void Sort_생성() {
		MartOrder.empty().getOrder();
	}

	@Test
	public void 이름으로_프로퍼티_조회() {
		assertThat(Property.of("branchName") == Property.branchName).isTrue();
	}

	@Test(expected = IllegalArgumentException.class)
	public void 잘못된_프로퍼티_조회() {
		Property.of("notFound");
	}

	private String createOrderValue(String property, String direction) {
		return property + MartOrder.SORT_DELIMITER + direction;
	}
}