package com.hongsi.martholidayalarm.bot.kakao.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.StringJoiner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private String text;

	private Photo photo;

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

	public static String makeBranchInfo(Mart mart) {
		final String TITLE_PREFIX = "※ ";
		final String ITEM_PREFIX = "\n• ";
		final String INFO_PREFIX = "  ▪︎ ";

		StringJoiner message = new StringJoiner(LINE_SEPARATOR);
		message.add(TITLE_PREFIX + mart.getMartType().getName() + " > " + mart.getBranchName())
				.add(ITEM_PREFIX + "주소\n" + INFO_PREFIX + mart.getAddress())
				.add(ITEM_PREFIX + "전화번호\n" + INFO_PREFIX + mart.getPhoneNumber())
				.add(ITEM_PREFIX + "휴일");
		LocalDate now = LocalDate.now();
		for (Holiday holiday : mart.getHolidays()) {
			LocalDate date = holiday.getHoliday();
			if (date.isBefore(now)) {
				continue;
			}
			message.add(INFO_PREFIX + date.toString() +
					" (" + date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)
					+ ")");
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
