package com.hongsi.martholidayalarm.client.slack.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SlackChannel {

    SERVER_ERROR("CN4LW9R16");

    @JsonValue
    private String id;
}
