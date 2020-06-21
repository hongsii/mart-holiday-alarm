package com.hongsi.martholidayalarm.client.slack.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SlackChannel {

    CRAWLING_ALARM("CN4LW9R16");

    @JsonValue
    private final String id;
}
