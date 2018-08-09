package com.hongsi.martholidayalarm.bot.kakao.dto;

import com.hongsi.martholidayalarm.bot.kakao.domain.UserRequest;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

	@NotBlank
	private String user_key;

	@NotBlank
	private String type;

	@NotBlank
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


