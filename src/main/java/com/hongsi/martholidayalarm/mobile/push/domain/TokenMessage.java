package com.hongsi.martholidayalarm.mobile.push.domain;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class TokenMessage {

	private static final ApnsConfig APNS_CONFIG = ApnsConfig.builder()
			.setAps(Aps.builder()
					.setSound("default")
					.setThreadId("martholidayapp")
					.build())
			.build();

	private String deviceToken;
	private Notification notification;

	public TokenMessage(String deviceToken, Notification notification) {
		this.deviceToken = deviceToken;
		this.notification = notification;
	}

	public static TokenMessage fromMart(String deviceToken, PushMart pushMart) {
		log.info("[PUSH][INFO] token : {}, Mart : {}", deviceToken, pushMart);
		return new TokenMessage(deviceToken, makeNotification(pushMart));
	}

	private static Notification makeNotification(PushMart mart) {
		String title = String.format("%s %s", mart.getMartType(), mart.getBranchName());
		String message = String.format("내일(%s)은 쉬는 날이에요!!🤗", mart.getFormattedHoliday());
		return new Notification(title, message);
	}

	public Message toFirebaseMessage() {
		return Message.builder()
				.setToken(deviceToken)
				.setNotification(notification)
				.setApnsConfig(APNS_CONFIG)
				.build();
	}
}
