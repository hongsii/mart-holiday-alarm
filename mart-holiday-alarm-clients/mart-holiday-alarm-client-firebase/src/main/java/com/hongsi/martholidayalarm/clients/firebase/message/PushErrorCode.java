package com.hongsi.martholidayalarm.clients.firebase.message;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum PushErrorCode {

    DELETED_TOKEN("registration-token-not-registered"), ERROR("unknown error");

    private final String errorCode;

    public static PushErrorCode of(String errorCode) {
        return Arrays.stream(values())
                .filter(pushErrorCode -> pushErrorCode.errorCode.equalsIgnoreCase(errorCode))
                .findFirst()
                .orElse(ERROR);
    }
}
