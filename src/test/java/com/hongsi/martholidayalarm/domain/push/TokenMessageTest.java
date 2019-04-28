package com.hongsi.martholidayalarm.domain.push;

import com.google.firebase.messaging.Notification;
import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

public class TokenMessageTest {

	@Test
	@Ignore
	public void 푸시_문구_확인() {
		PushMart pushMart = PushMart.builder()
                .id(1L)
				.martType(MartType.EMART)
				.branchName("foo점")
				.holiday(Holiday.of(LocalDate.of(2018, 1, 1)))
				.build();
		TokenMessage tokenMessage = TokenMessage.fromMart("token", pushMart);
		Notification notification = tokenMessage.getNotification();

		Arrays.stream(notification.getClass().getDeclaredFields())
				.forEach(field -> {
					try {
						field.setAccessible(true);
						String name = field.getName();
						Object value = field.get(notification);
						System.out.println(name + " : " + value.toString());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				});
	}
}