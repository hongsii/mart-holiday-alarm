package com.hongsi.martholidayalarm.bot.kakao.dto;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import lombok.Getter;

@Getter
public class MessageButton {

	private String label;

	private String url;

	public MessageButton(String label, String url) {
		this.label = label;
		this.url = url;
	}

	public MessageButton(Mart mart) {
		label = mart.getBranchName() + " 상세보기";
		url = mart.getUrl();
	}
}
