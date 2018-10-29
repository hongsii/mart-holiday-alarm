package com.hongsi.martholidayalarm.mobile.push.firebase;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Message.Builder;
import com.google.firebase.messaging.Notification;
import com.hongsi.martholidayalarm.common.exception.NoHolidayException;
import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.dto.MartDto;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirebaseMessageSender {

	public static String sendToToken(String token, Notification notification) {
		Message message = getDefaultMessage(notification)
				.setToken(token)
				.build();
		return send(message);
	}

	public static String sendToTopic(String topic, Notification notification) {
		Message message = getDefaultMessage(notification)
				.setTopic(topic)
				.build();
		return send(message);
	}

	public static void sendToToken(String token, List<MartDto> marts) {
		log.info("token : {} / mart size : {}", token, marts.size());
		for (MartDto mart : marts) {
			try {
				log.info("id : {} / branchName : {}", mart.getId(), mart.getBranchName());
				Message message = getDefaultMessage(makeNotification(mart))
						.setToken(token)
						.build();
				String messageId = send(message);
				log.info("message id : {}", messageId);
			} catch (NoHolidayException e) {
				log.error("{} [token : {}, martId : {}]", e.getMessage(), token, mart.getId());
			}
		}
	}

	private static Notification makeNotification(MartDto mart) throws NoHolidayException {
		String title = String.format("%s %s", mart.getMartType(), mart.getBranchName());
		LocalDate holiday = LocalDate.parse(mart.getUpcomingHoliday()
				, Holiday.DEFAULT_FORMATTER_WITH_DAYOFWEEK);
		String message = String.format("내일(%s)은 쉬는 날이에요!!🤗"
				, holiday.format(Holiday.DAYOFMONTH_FORMATTER));
		return new Notification(title, message);
	}

	private static Builder getDefaultMessage(Notification notification) {
		return Message.builder()
				.setNotification(notification)
				.setApnsConfig(getApnsConfig());
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
