package com.hongsi.martholidayalarm.scheduler;

import com.hongsi.martholidayalarm.constants.ProfileType;
import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.push.PushMart;
import com.hongsi.martholidayalarm.domain.push.User;
import com.hongsi.martholidayalarm.service.FavoriteService;
import com.hongsi.martholidayalarm.service.MartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.hongsi.martholidayalarm.client.firebase.FirebaseMessageClient.sendTokenMessages;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({ProfileType.PROD1, ProfileType.PROD2})
public class PushScheduler {

	private final MartService martService;
	private final FavoriteService favoriteService;

	@Scheduled(cron = "${schedule.cron.push:0 0 11 ? * *}")
	public void sendHolidayPushToUser() {
		List<User> users = favoriteService.getUsers();

		Holiday tomorrow = Holiday.of(LocalDate.now().plusDays(1));
		List<PushMart> marts = martService.findPushMartsByHoliday(tomorrow);
		log.info("[PUSH] Tomorrow holiday : {}", tomorrow.toString());

		long successMessageCount = users.stream()
				.flatMap(user -> sendTokenMessages(user.makeTokenMessages(marts)).stream())
				.count();
		log.info("[PUSH] Success message count : {}", successMessageCount);
	}
}
