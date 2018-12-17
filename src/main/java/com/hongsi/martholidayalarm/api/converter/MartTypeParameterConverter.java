package com.hongsi.martholidayalarm.api.converter;

import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.beans.PropertyEditorSupport;

public class MartTypeParameterConverter extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String capitalized = text.toUpperCase();
		MartType martType = MartType.valueOf(capitalized);
		setValue(martType);
	}
}

