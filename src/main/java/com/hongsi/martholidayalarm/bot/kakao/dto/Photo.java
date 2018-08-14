package com.hongsi.martholidayalarm.bot.kakao.dto;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

@Getter
@Setter
@ApiIgnore(value = "미지원")
public class Photo {

	private String url;

	private String width;

	private String height;
}
