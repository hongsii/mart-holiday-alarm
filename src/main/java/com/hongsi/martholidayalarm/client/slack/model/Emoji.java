package com.hongsi.martholidayalarm.client.slack.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Emoji {

    FIRE;

    private static final String AFFIX = ":";

    @JsonValue
    @Override
    public String toString() {
        return AFFIX + name() + AFFIX;
    }
}
