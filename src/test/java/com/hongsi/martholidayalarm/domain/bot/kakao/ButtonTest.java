package com.hongsi.martholidayalarm.domain.bot.kakao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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