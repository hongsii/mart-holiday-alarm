package com.hongsi.martholidayalarm.common.utils;

import java.util.Objects;

public class ValidationUtils {

	public static boolean isNotBlank(Object object) {
		boolean isNotBlank = Objects.nonNull(object);
		if (object instanceof String) {
			return isNotBlank && !"".equals(object.toString().trim());
		}
		return isNotBlank;
	}
}
