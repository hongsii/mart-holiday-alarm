package com.hongsi.martholidayalarm.service.dto.mart;

import com.hongsi.martholidayalarm.domain.mart.MartType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MartTypeDto {

	@Getter
	@Setter
	public static class Response {

		private String value;
		private String displayName;

		@Builder
		private Response(String value, String displayName) {
			this.value = value;
			this.displayName = displayName;
		}

		public static Response of(MartType martType) {
			return Response.builder()
					.value(martType.name().toLowerCase())
					.displayName(martType.getDisplayName())
					.build();
		}
	}
}
