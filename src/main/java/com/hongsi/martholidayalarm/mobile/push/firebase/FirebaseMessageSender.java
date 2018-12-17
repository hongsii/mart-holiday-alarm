package com.hongsi.martholidayalarm.mobile.push.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.hongsi.martholidayalarm.mobile.push.domain.TokenMessage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirebaseMessageSender {

	public static List<String> sendTokenMessages(List<TokenMessage> tokenMessages) {
		return tokenMessages.stream()
				.map(FirebaseMessageSender::sendTokenMessage)
				.collect(Collectors.toList());
	}

	public static String sendTokenMessage(TokenMessage tokenMessage) {
		return send(tokenMessage.toFirebaseMessage());
	}

	private static String send(Message message) {
		String messageId = null;
		try {
			messageId = FirebaseMessaging.getInstance(FirebaseAppManager.getInstance())
					.send(message);
		} catch (FirebaseMessagingException e) {
			log.error("[ERROR][PUSH][SEND] Message can't send -> ErrorCode : {}, Message : {}",
					e.getErrorCode(),
					e.getMessage());
		}
		return messageId;
	}
}
