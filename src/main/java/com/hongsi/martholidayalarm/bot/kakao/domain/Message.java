package com.hongsi.martholidayalarm.bot.kakao.domain;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import java.time.LocalDate;
import java.util.StringJoiner;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Message {

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private String text;

	private Photo photo;

	private MessageButton messageButton;

	public Message(String text, Photo photo, MessageButton messageButton) {
		this.text = text;
		this.photo = photo;
		this.messageButton = messageButton;
	}

	public static String makeBranchInfo(Mart mart) {
		StringJoiner message = new StringJoiner(LINE_SEPARATOR);
		message.add("※ " + mart.getBranchName() + "\n")
				.add("* 주소\n  - " + mart.getAddress())
				.add("* 전화번호\n  - " + mart.getPhoneNumber())
				.add("* 휴일");

		LocalDate now = LocalDate.now();
		for (Holiday holiday : mart.getHolidays()) {
			LocalDate date = holiday.getHoliday();
			if (date.isBefore(now)) {
				continue;
			}
			message.add("  - " + date.toString());
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
