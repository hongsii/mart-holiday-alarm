package com.hongsi.martholidayalarm.mobile.push.domain;

import com.google.firebase.messaging.Notification;
import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Ignore;
import org.junit.Test;

public class TokenMessageTest {

	@Test
	@Ignore
	public void 푸시_문구_확인() {
		PushMart pushMart = new PushMart(1L, MartType.EMART, "foo점",
				Holiday.of(LocalDate.of(2018, 1, 1)));

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