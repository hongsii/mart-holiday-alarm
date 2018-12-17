package com.hongsi.martholidayalarm.mart.domain;

import java.util.Arrays;

public enum MartType {

	EMART("이마트"),
	EMART_TRADERS("이마트 트레이더스"),
	LOTTEMART("롯데마트"),
	HOMEPLUS("홈플러스"),
	HOMEPLUS_EXPRESS("홈플러스 익스프레스");

	private String name;

	MartType(String name) {
		this.name = name;
	}

	public static MartType of(String name) {
		return Arrays.stream(values())
				.filter(martType -> martType.isSameMartName(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("해당 마트 타입이 존재하지 않습니다."));
	}

	public boolean isSameMartName(String name) {
		return this.name.equals(name);
	}

	public String getDisplayName() {
		return name;
	}
}
