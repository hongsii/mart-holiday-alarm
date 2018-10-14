package com.hongsi.martholidayalarm.mobile.push.firebase;

import com.hongsi.martholidayalarm.common.exception.NoHolidayException;
import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.dto.MartDto;
import com.hongsi.martholidayalarm.common.mart.service.MartService;
import com.hongsi.martholidayalarm.mobile.push.firebase.service.FavoriteService;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

	@Scheduled(cron = "${schedule.cron.push:0 0 11 ? * *}")
	public void notifyHoliday() {
		Set<Long> ids = favoriteService.getFavoritedMartIds();
		Holiday holiday = Holiday.builder()
				.date(LocalDate.now().plusDays(1))
				.build();
		log.info("PushScheduler current time : {}", LocalDateTime.now());
		log.info("PushScheduler holiday : {}", holiday.toString());
		List<MartDto> marts = martService.getMartsHavingSameHoliday(ids, holiday);

		log.info("PushScheduler favorite id size : {}", ids.size());
		log.info("PushScheduler mart size for push : {}", marts.size());

		for (MartDto mart : marts) {
			try {
				FirebaseMessageSender.sendToTopic(mart);
			} catch (NoHolidayException e) {
				log.error("{} [martId : {}]", e.getMessage(), mart.getId());
			}
		}
	}
}
