package com.hongsi.martholidayalarm.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class ApiLog {

    private String remoteAddr;
    private String httpMethod;
    private String urlPattern;
    private String queryString;

    @Builder
    public ApiLog(String remoteAddr, String httpMethod, String urlPattern, String queryString) {
        this.remoteAddr = remoteAddr;
        this.httpMethod = httpMethod;
        this.urlPattern = urlPattern;
        this.queryString = Objects.toString(queryString, "");
    }
}
