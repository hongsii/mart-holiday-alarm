package com.hongsi.martholidayalarm.bot.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.StringJoiner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "응답 결과", description = "사용자 요청에 따른 응답 결과")
public class Message {

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@ApiModelProperty(name = "내용", value = "text", dataType = "java.lang.String")
	private String text;

	@ApiModelProperty(name = "이미지", value = "photo", dataType = "com.hongsi.martholidayalarm.bot.kakao.dto.Photo")
	private Photo photo;

	@ApiModelProperty(name = "링크 버튼", value = "message_button", dataType = "com.hongsi.martholidayalarm.bot.kakao.dto.MessageButton")
	@JsonProperty("message_button")
	private MessageButton messageButton;

	public Message(String text) {
		this.text = text;
	}

	public Message(String text, MessageButton messageButton) {
		this.text = text;
		this.messageButton = messageButton;
	}

	public Message(String text, Photo photo, MessageButton messageButton) {
		this.text = text;
		this.photo = photo;
		this.messageButton = messageButton;
	}

	public Message(Mart mart) {
		text = makeBranchInfo(mart);
		messageButton = new MessageButton(mart);
	}

	private String makeBranchInfo(Mart mart) {
		final String TITLE_PREFIX = "※ ";
		final String ITEM_PREFIX = "\n• ";
		final String INFO_PREFIX = "  ▪︎ ";

		StringJoiner message = new StringJoiner(LINE_SEPARATOR);
		message.add(TITLE_PREFIX + mart.getMartType().getName() + " > " + mart.getBranchName())
				.add(ITEM_PREFIX + "주소\n" + INFO_PREFIX + mart.getAddress())
				.add(ITEM_PREFIX + "전화번호\n" + INFO_PREFIX + mart.getPhoneNumber())
				.add(ITEM_PREFIX + "휴일");
		for (Holiday holiday : mart.getHolidays()) {
			if (holiday.isUpcoming()) {
				message.add(INFO_PREFIX + holiday.getFormattedHolidayWithDayOfWeek());
			}
		}
		return message.toString();
	}

	@Override
	public String toString() {
		return "Message{" +
				"text='" + text + '\'' +
				", photo=" + photo +
				", messageButton=" + messageButton +
				'}';
	}
}
