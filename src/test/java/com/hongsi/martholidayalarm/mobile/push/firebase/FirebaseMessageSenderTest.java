package com.hongsi.martholidayalarm.mobile.push.firebase;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.firebase.messaging.Notification;
import java.util.Arrays;
import org.junit.Test;

public class FirebaseMessageSenderTest {

	@Test
	public void sendToToken() {
		Notification notification = new Notification("토큰", "토큰으로 노티 전송");
		String messageId = FirebaseMessageSender.sendToToken(getTestToken(), notification);
		System.out.println(messageId);

		assertThat(messageId).isNotBlank();
	}

	@Test
	public void sendToTopic() throws Exception {
		String topic = "test-topic";
		FirebaseMessageTopicManager.subscribe(Arrays.asList(getTestToken()), topic);

		Notification notification = new Notification(topic, "토픽으로 노티 전송");
		String messageId = FirebaseMessageSender.sendToTopic(topic, notification);

		assertThat(messageId).isNotBlank();

		FirebaseMessageTopicManager.unsubscribe(Arrays.asList(getTestToken()), topic);
	}

	private String getTestToken() {
		return "c0Uzp846pjA:APA91bGtxWHnC90aeGAjYD2AJIQaovoKdCLq3zIXpfOyYMrqJPWNzItAVOTVXUcw5IZaxyP675Wx06_6jw1WjUalrmydHbogE84EdJqHY7LLA1iC1Oe0xgcqVcbEDop1hEb5J9-hrPFI";
	}
}