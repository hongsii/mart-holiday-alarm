package com.hongsi.martholidayalarm.mobile.push.firebase;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.dto.MartDto;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import com.hongsi.martholidayalarm.mobile.push.firebase.service.FavoriteService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PushScheduler {

	private final MartService martService;
	private final FavoriteService favoriteService;

	//		@Scheduled(initialDelay = 9000, fixedDelay = 90000)
	@Scheduled(cron = "0 0 9 ? * *")
	public void notifyHoliday() {
		favoriteService.removeFavoritedMart(Arrays.asList(1L, 140L));

		Set<Long> ids = favoriteService.getFavoritedMartIds();
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		Holiday holiday = Holiday.builder()
				.date(tomorrow)
				.build();
		List<MartDto> marts = martService.getMartsHavingSameHoliday(ids, holiday);

		log.info("PushScheduler favorite id size : {}", ids.size());
		log.info("PushScheduler mart size for push : {}", marts.size());

		for (MartDto mart : marts) {
			FirebaseMessageSender.sendToTopic(mart.getId().toString(),
					FirebaseMessageSender.makeNotification(mart));
		}
	}

	@Scheduled(initialDelay = 9000, fixedDelay = 90000)
	public void test() {
		favoriteService.removeFavoritedMart(Arrays.asList(1L, 140L));
	}
}
