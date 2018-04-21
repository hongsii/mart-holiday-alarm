package com.hongsi.martholidayalarm.bot.kakao.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Keyboard {

	public static final String[] DEFAULT_KEYBOARD = {"마트 휴일 조회"};

	private String type = "buttons";

	private String[] buttons;

	@Builder
	public Keyboard(String type, String[] buttons) {
		if (type != null) {
			this.type = type;
		}
		this.buttons = buttons;
	}

	public static List<String> getDefaultKeyboardToList() {
		return Arrays.asList(DEFAULT_KEYBOARD);
	}

	@Override
	public String toString() {
		return "Keyboard{" +
				"type='" + type + '\'' +
				", buttons=" + Arrays.toString(buttons) +
				'}';
	}
}
