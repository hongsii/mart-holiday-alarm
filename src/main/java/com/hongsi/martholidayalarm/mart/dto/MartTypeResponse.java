package com.hongsi.martholidayalarm.mart.dto;

import com.hongsi.martholidayalarm.mart.domain.MartType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MartTypeResponse {

	private String value;
	private String displayName;

	public MartTypeResponse(String value, String displayName) {
		this.value = value;
		this.displayName = displayName;
	}

	public static MartTypeResponse from(MartType martType) {
		return new MartTypeResponse(martType.name(), martType.getDisplayName());
	}
}
