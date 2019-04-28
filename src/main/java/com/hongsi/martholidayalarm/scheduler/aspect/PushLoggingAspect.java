package com.hongsi.martholidayalarm.scheduler.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PushLoggingAspect {

	private StopWatch stopWatch;

	@Before("execution(* com.hongsi.martholidayalarm.scheduler.PushScheduler.sendHolidayPushToUser())")
	public void start() {
		log.info("============ Start Push ============");
		stopWatch = new StopWatch("Push");
	}

	@After("execution(* com.hongsi.martholidayalarm.scheduler.PushScheduler.sendHolidayPushToUser())")
	public void end() {
		if (stopWatch.isRunning()) {
			stopWatch.stop();
		}

		log.info(stopWatch.prettyPrint());
		log.info("============ End Push (total : {}) ============",
				stopWatch.getTotalTimeSeconds());
	}
}
