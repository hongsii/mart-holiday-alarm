package com.hongsi.martholidayalarm.mart.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MartType {
	EMART("E", "EMART", true), LOTTEMART("L", "LOTTEMART", false);

	private String type;
	private String name;
	private boolean isUsing;

	MartType(String type, String name, boolean isUsing) {
		this.type = type;
		this.name = name;
		this.isUsing = isUsing;
	}

	public static boolean isMartType(String content) {
		return Arrays.stream(MartType.values())
				.anyMatch(martType -> martType.getName().equals(content));
	}
}
