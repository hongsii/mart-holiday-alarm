package com.hongsi.martholidayalarm.bot.kakao.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ButtonTest {

	@Test
	public void 다음_버튼_조회() {
		Button prevButton = Button.MARTTYPE;

		Button nextButton = prevButton.getNextButton();

		assertEquals(nextButton, Button.REGION);
	}

	@Test
	public void 마지막_버튼_조회() {
		Button prevButton = Button.BRANCH;

		Button nextButton = prevButton.getNextButton();

		assertEquals(nextButton, Button.DEFAULT);
	}
}