package com.hongsi.martholidayalarm.service.dto.bot.kakao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(value = "봇 응답", description = "사용자의 요청에 대한 응답")
public class BotResponse {

	@ApiModelProperty(name = "응답 결과", value = "message", dataType = "java.lang.String", required = true)
	private Message message;

	@ApiModelProperty(name = "추가 요청 버튼", value = "keyboard", dataType = "java.lang.String", required = true)
	private Keyboard keyboard;

	public BotResponse(Message message, Keyboard keyboard) {
		this.message = message;
		this.keyboard = keyboard;
	}

	@Override
	public String toString() {
		return "BotResponse{" +
				"message=" + message +
				", keyboard=" + keyboard +
				'}';
	}
}
