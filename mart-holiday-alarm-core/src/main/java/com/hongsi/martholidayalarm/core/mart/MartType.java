package com.hongsi.martholidayalarm.core.mart;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MartType {

	EMART("이마트"),
	EMART_TRADERS("이마트 트레이더스"),
	NOBRAND("노브랜드"),
	LOTTEMART("롯데마트"),
	HOMEPLUS("홈플러스"),
	HOMEPLUS_EXPRESS("홈플러스 익스프레스"),
	COSTCO("코스트코");

	private String name;

	MartType(String name) {
		this.name = name;
	}

	public static MartType of(String name) {
		return Arrays.stream(values())
				.filter(martType -> martType.name.equals(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("잘못된 마트명입니다."));
	}
}
