package com.hongsi.martholidayalarm.mobile.push.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirebaseMessageTopicManager {

	public static void subscribe(List<String> registrationTokens, String topic)
			throws InterruptedException, ExecutionException {
		TopicManagementResponse response = getInstance()
				.subscribeToTopicAsync(registrationTokens, topic).get();
		log.info("[Subscribe] topic : {}, token count : {}", topic, registrationTokens.size());
		printResult(response);
	}

	public static void unsubscribe(List<String> registrationTokens, String topic)
			throws InterruptedException, ExecutionException {
		TopicManagementResponse response = getInstance()
				.unsubscribeFromTopicAsync(registrationTokens, topic).get();
		log.info("[Unsubscribe] topic : {}, token count : {}", topic, registrationTokens.size());
		printResult(response);
	}

	public static void unsubscribe(Map<String, List<String>> topics)
			throws InterruptedException, ExecutionException {
		for (String topic : topics.keySet()) {
			List<String> registrationTokens = topics.get(topic);
			unsubscribe(registrationTokens, topic);
		}
	}

	public static FirebaseMessaging getInstance() {
		return FirebaseMessaging.getInstance(FirebaseAppManager.getInstance());
	}

	private static void printResult(TopicManagementResponse response) {
		log.info("Success tokens : {}", response.getSuccessCount());
		log.info("Failure tokens : {}", response.getFailureCount());
		List<TopicManagementResponse.Error> errors = response.getErrors();
		if (!errors.isEmpty()) {
			for (TopicManagementResponse.Error error : errors) {
				log.error("Error index : {} / reason : {}", error.getIndex(), error.getReason());
			}
		}
	}
}
