package com.hongsi.martholidayalarm.push.model;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class MartPushMessage {

    private static final ApnsConfig APNS_CONFIG = ApnsConfig.builder()
            .setAps(Aps.builder()
                    .setSound("default")
                    .setThreadId("martholidayapp")
                    .build())
            .build();

    private final String deviceToken;
    private final String title;
    private final String message;

    MartPushMessage(String deviceToken, String title, String message) {
        this.deviceToken = deviceToken;
        this.title = title;
        this.message = message;
    }

    public static MartPushMessage of(String deviceToken, PushMart pushMart) {
        String title = String.format("%s %s", pushMart.getMartType(), pushMart.getBranchName());
        String message = String.format("내일(%s)은 쉬는 날이에요!! 🏖", pushMart.getFormattedHoliday());
        return new MartPushMessage(deviceToken, title, message);
    }

    public Message toFirebaseMessage() {
        return Message.builder()
                .setToken(deviceToken)
                .setNotification(new Notification(title, message))
                .setApnsConfig(APNS_CONFIG)
                .build();
    }
}
