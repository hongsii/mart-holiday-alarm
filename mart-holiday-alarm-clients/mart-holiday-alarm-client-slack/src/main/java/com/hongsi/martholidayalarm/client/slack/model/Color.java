package com.hongsi.martholidayalarm.client.slack.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Color {

    RED("#FF0000"),
    LIME("#00FF00");

    @JsonValue
    private final String hex;
}
