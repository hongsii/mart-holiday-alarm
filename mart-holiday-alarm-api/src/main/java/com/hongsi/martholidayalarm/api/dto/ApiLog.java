package com.hongsi.martholidayalarm.api.dto;

import lombok.Builder;
import lombok.ToString;

import java.util.Objects;

@ToString
public class ApiLog {

    private final String remoteAddr;
    private final String httpMethod;
    private final String urlPattern;
    private final String queryString;

    @Builder
    public ApiLog(String remoteAddr, String httpMethod, String urlPattern, String queryString) {
        this.remoteAddr = remoteAddr;
        this.httpMethod = httpMethod;
        this.urlPattern = urlPattern;
        this.queryString = String.format("'%s'", Objects.toString(queryString, ""));
    }
}
