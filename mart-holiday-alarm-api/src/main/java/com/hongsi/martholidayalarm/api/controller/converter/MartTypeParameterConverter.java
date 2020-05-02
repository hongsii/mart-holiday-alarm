package com.hongsi.martholidayalarm.api.controller.converter;

import com.hongsi.martholidayalarm.api.exception.InvalidMartTypeException;
import com.hongsi.martholidayalarm.core.mart.MartType;

import java.beans.PropertyEditorSupport;

public class MartTypeParameterConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            String capitalized = text.toUpperCase();
            MartType martType = MartType.valueOf(capitalized);
            setValue(martType);
        } catch (IllegalArgumentException e) {
            throw new InvalidMartTypeException(text);
        }
    }
}

