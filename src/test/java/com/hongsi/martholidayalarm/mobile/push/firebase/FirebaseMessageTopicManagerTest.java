package com.hongsi.martholidayalarm.mobile.push.firebase;


import java.util.Arrays;
import org.junit.Test;

public class FirebaseMessageTopicManagerTest {

	@Test
	public void subscribe() throws Exception {
		FirebaseMessageTopicManager.subscribe(Arrays.asList(
				"e6Ym4_mHCck:APA91bHAOhsoF19jqL-0kTQyOjEyGfDVC_5-FifQOSFupZLqxR2kTwdbAHk4u_AQHuGwicPQJwo96zt8V4gpvvmuOeYf9P19wVQP3bhsObgRgLrkLcJjk1ck2C6GS4sX-463ZN2RT0jo"),
				"test");
	}

	@Test
	public void unsubscribe() throws Exception {
		FirebaseMessageTopicManager.unsubscribe(Arrays.asList(
				"e6Ym4_mHCck:APA91bHAOhsoF19jqL-0kTQyOjEyGfDVC_5-FifQOSFupZLqxR2kTwdbAHk4u_AQHuGwicPQJwo96zt8V4gpvvmuOeYf9P19wVQP3bhsObgRgLrkLcJjk1ck2C6GS4sX-463ZN2RT0jo"),
				"test");
	}
}