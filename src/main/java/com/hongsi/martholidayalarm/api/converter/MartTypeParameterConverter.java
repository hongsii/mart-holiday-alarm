package com.hongsi.martholidayalarm.api.converter;

import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.exception.CannotParseMartTypeException;
import java.beans.PropertyEditorSupport;

public class MartTypeParameterConverter extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			String capitalized = text.toUpperCase();
			MartType martType = MartType.valueOf(capitalized);
			setValue(martType);
		} catch (IllegalArgumentException e) {
			throw new CannotParseMartTypeException(text);
		}
	}
}

