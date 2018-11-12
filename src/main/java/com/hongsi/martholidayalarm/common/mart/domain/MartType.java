package com.hongsi.martholidayalarm.common.mart.domain;

import lombok.Getter;

@Getter
public enum MartType {
	EMART("이마트"),
	LOTTEMART("롯데마트"),
	HOMEPLUS("홈플러스"),
	HOMEPLUS_EXPRESS("홈플러스 익스프레스");

	private String name;

	MartType(String name) {
		this.name = name;
	}

	public static MartType of(String name) {
		for (MartType martType : MartType.values()) {
			if (martType.name.equals(name)) {
				return martType;
			}
		}
		throw new IllegalArgumentException("해당 마트 타입이 존재하지 않습니다.");
	}
}
