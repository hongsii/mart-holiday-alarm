package com.hongsi.martholidayalarm.exception;

import com.hongsi.martholidayalarm.domain.mart.MartType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CannotParseMartTypeException extends RuntimeException {

	private static final Map<String, String> MART_TYPES = new HashMap<>(1);

	static {
		MART_TYPES.put("martTypes", Arrays.stream(MartType.values())
				.map(MartType::name)
				.collect(Collectors.joining(", "))
		);
	}

	private String requestValue;

	public CannotParseMartTypeException(String requestValue) {
		this.requestValue = requestValue;
	}

	@Override
	public String getMessage() {
		return String.format("존재하지 않는 마트타입입니다. [value : %s]", requestValue);
	}

	public Map<String, String> getPossibleMartTypes() {
		return MART_TYPES;
	}
}
