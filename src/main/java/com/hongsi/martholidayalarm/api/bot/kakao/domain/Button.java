package com.hongsi.martholidayalarm.api.bot.kakao.domain;

import lombok.Getter;

@Getter
public enum Button {
	DEFAULT(1, "마트를 선택해주세요"), MARTTYPE(2, "지역을 선택해주세요"), REGION(3, "점포를 선택해주세요"), BRANCH(4, " ");

	final int order;
	final String message;

	Button(int order, String message) {
		this.order = order;
		this.message = message;
	}

	public Button getNextButton() {
		for (Button button : Button.values()) {
			if (button.order == order + 1) {
				return button;
			}
		}
		return DEFAULT;
	}
}
