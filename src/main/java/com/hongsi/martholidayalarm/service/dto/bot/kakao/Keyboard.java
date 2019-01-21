package com.hongsi.martholidayalarm.service.dto.bot.kakao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
@ApiModel(value = "버튼")
public class Keyboard {

	public static final String[] DEFAULT_KEYBOARD = {"마트 휴일 조회"};

	@ApiModelProperty(name = "타입", value = "type", dataType = "java.lang.String", required = true, example = "buttons")
	private String type;

	@ApiModelProperty(name = "요청 버튼", value = "buttons", dataType = "java.lang.String[]", required = true, example = "[ 마트 휴일 조회 ]")
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
