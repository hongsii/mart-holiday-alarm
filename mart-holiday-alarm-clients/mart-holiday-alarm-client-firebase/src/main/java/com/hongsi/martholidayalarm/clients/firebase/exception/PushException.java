package com.hongsi.martholidayalarm.clients.firebase.exception;

import com.hongsi.martholidayalarm.clients.firebase.message.PushErrorCode;

public class PushException extends RuntimeException {

    private final PushErrorCode errorCode;

    public PushException(String errorCode, String message) {
        super(message);
        this.errorCode = PushErrorCode.of(errorCode);
    }

    public boolean isDeletedToken() {
        return errorCode == PushErrorCode.DELETED_TOKEN;
    }
}
