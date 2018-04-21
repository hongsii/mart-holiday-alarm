package com.hongsi.martholidayalarm.bot.kakao.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

	private String user_key;

	private String type;

	private String content;

	public UserRequest toEntity() {
		return UserRequest.builder()
				.userKey(user_key)
				.type(type)
				.content(content)
				.build();
	}

	@Override
	public String toString() {
		return "UserRequestDTO{" +
				"user_key='" + user_key + '\'' +
				", type='" + type + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}


