package com.hongsi.martholidayalarm.client.slack.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Color {

    RED("#FF0000");

    @JsonValue
    private String hex;
}
