package com.hongsi.martholidayalarm.bot.kakao.dto;

import com.hongsi.martholidayalarm.bot.kakao.domain.UserRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "사용자 요청 정보")
public class UserRequestDto {

	@ApiModelProperty(name = "사용자 고유키", value = "user_key", dataType = "java.lang.String", required = true, example = "1234")
	@NotBlank
	private String user_key;

	@ApiModelProperty(name = "요청타입", value = "type", dataType = "java.lang.String", required = true, example = "buttons")
	@NotBlank
	private String type;

	@ApiModelProperty(name = "클릭한 버튼", value = "content", dataType = "java.lang.String", required = true, example = "마트 휴일 조회")
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


