package com.hongsi.martholidayalarm.mobile.push.firebase;

import static com.hongsi.martholidayalarm.common.mart.domain.QHoliday.holiday;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.dto.MartDto;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import com.hongsi.martholidayalarm.mobile.push.firebase.domain.User;
import com.hongsi.martholidayalarm.mobile.push.firebase.service.FavoriteService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@RequiredArgsConstructor
@Slf4j
public class PushScheduler {

	private final MartService martService;
	private final FavoriteService favoriteService;

	private StopWatch stopWatch;

	@Scheduled(cron = "${schedule.cron.push:0 0 11 ? * *}")
	public void notifyHolidayToUser() {
		stopWatch = new StopWatch("MobilePush");

		stopWatch.start("FirebaseUsers");
		List<User> users = favoriteService.getUsers();
		stopWatch.stop();

		stopWatch.start("marts");
		Holiday tomorrow = Holiday.builder()
				.date(LocalDate.now().plusDays(1))
				.build();
		List<MartDto> marts = martService.getMartsHavingSameHoliday(tomorrow);
		stopWatch.stop();

		stopWatch.start("pushToToken");
		for (User user : users) {
			List<MartDto> favoritedMarts = marts.stream()
					.filter(martDto -> user.hasSameMartId(martDto.getId()))
					.collect(Collectors.toList());
			FirebaseMessageSender.sendToToken(user.getDeviceToken(), favoritedMarts);
		}
		stopWatch.stop();

		log.info("PushScheduler target holiday : {}", holiday.toString());
		log.info("PushScheduler users : {}", users.size());
		log.info(stopWatch.prettyPrint());
	}
}
