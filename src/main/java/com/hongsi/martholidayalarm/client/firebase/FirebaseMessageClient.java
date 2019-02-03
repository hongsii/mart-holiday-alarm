package com.hongsi.martholidayalarm.client.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.hongsi.martholidayalarm.domain.push.TokenMessage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirebaseMessageClient {

	public static List<String> sendTokenMessages(List<TokenMessage> tokenMessages) {
		return tokenMessages.stream()
				.map(FirebaseMessageClient::sendTokenMessage)
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
		} catch (Exception e) {
			log.error("[ERROR][PUSH][SEND] Message Error -> Message : {}, Cause : {}", e.getMessage(), e.getCause());
		}
		return messageId;
	}
}
