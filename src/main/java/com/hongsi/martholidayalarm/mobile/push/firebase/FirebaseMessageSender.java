package com.hongsi.martholidayalarm.mobile.push.firebase;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.hongsi.martholidayalarm.common.mart.dto.MartDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirebaseMessageSender {

	public static String sendToToken(String token, Notification notification) {
		Message message = Message.builder()
				.setToken(token)
				.setNotification(notification)
				.setApnsConfig(getApnsConfig())
				.build();
		return send(message);
	}

	public static String sendToTopic(String topic, Notification notification) {
		Message message = Message.builder()
				.setTopic(topic)
				.setNotification(notification)
				.setApnsConfig(getApnsConfig())
				.build();
		return send(message);
	}

	public static Notification makeNotification(MartDto mart) {
		String title = String.format("%s %s", mart.getMartType(), mart.getBranchName());
		String message = String.format("내일[%s] 쉬는 날입니다.", mart.getHolidays().get(0));
		return new Notification(title, message);
	}

	private static ApnsConfig getApnsConfig() {
		return ApnsConfig.builder()
				.setAps(Aps.builder()
						.setSound("default")
						.setThreadId("martholidayapp")
						.build())
				.build();
	}

	private static String send(Message message) {
		String messageId = null;
		try {
			messageId = FirebaseMessaging.getInstance(FirebaseManager.getInstance()).send(message);
			log.info("Message send success -> messageId : {}", messageId);
		} catch (FirebaseMessagingException e) {
			log.error("Message can't send -> ErrorCode : {}, Message : {}", e.getErrorCode(),
					e.getMessage());
		}
		return messageId;
	}
}
