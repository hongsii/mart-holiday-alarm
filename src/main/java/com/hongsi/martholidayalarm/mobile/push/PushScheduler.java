package com.hongsi.martholidayalarm.mobile.push;

import static com.hongsi.martholidayalarm.mobile.push.firebase.FirebaseMessageSender.sendTokenMessages;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import com.hongsi.martholidayalarm.mart.service.MartService;
import com.hongsi.martholidayalarm.mobile.push.domain.User;
import com.hongsi.martholidayalarm.mobile.push.service.FavoriteService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"prod1", "prod2"})
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
