package com.hongsi.martholidayalarm.service.dto.bot.kakao;

import com.hongsi.martholidayalarm.service.dto.mart.MartDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(value = "링크 버튼", description = "응답과 관련된 링크")
public class MessageButton {

	@ApiModelProperty(name = "내용", value = "label", dataType = "java.lang.String", required = true)
	private String label;

	@ApiModelProperty(name = "주소", value = "url", dataType = "java.lang.String", required = true)
	private String url;

	public MessageButton(String label, String url) {
		this.label = label;
		this.url = url;
	}

	public MessageButton(MartDto.Response mart) {
		label = mart.getBranchName() + " 상세보기";
		url = mart.getUrl();
	}
}