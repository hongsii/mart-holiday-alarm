package com.hongsi.martholidayalarm.mart.domain;

import lombok.Getter;

@Getter
public enum MartType {
	EMART("이마트", true), LOTTEMART("롯데마트", false);

	private String name;
	private boolean isUsing;

	MartType(String name, boolean isUsing) {
		this.name = name;
		this.isUsing = isUsing;
	}

	public static MartType of(String name) throws NoSuchFieldException {
		for (MartType martType : MartType.values()) {
			if (martType.name.equals(name)) {
				return martType;
			}
		}
		throw new NoSuchFieldException("해당 마트 타입이 존재하지 않습니다.");
	}
}
