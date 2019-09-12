package com.hongsi.martholidayalarm.client.slack.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Emoji {

    FIRE, ZAP;

    private static final String AFFIX = ":";

    @JsonValue
    @Override
    public String toString() {
        return AFFIX + name() + AFFIX;
    }
}
