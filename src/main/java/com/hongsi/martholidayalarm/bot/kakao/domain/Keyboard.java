package com.hongsi.martholidayalarm.bot.kakao.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class Keyboard {

	public static final String[] DEFAULT_KEYBOARD = {"마트 휴일 조회"};

	private String type;

	private String[] buttons;

	public Keyboard(String type, String[] buttons) {
		this.type = type;
		this.buttons = buttons;
	}

	public Keyboard(String[] buttons) {
		type = "buttons";
		this.buttons = buttons;
	}

	public Keyboard(List<String> buttons) {
		type = "buttons";
		this.buttons = buttons
				.stream()
				.toArray(String[]::new);
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
