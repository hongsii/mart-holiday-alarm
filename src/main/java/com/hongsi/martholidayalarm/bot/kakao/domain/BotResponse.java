package com.hongsi.martholidayalarm.bot.kakao.domain;

import lombok.Getter;

@Getter
public class BotResponse {

	private Message message;

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
