package com.hongsi.martholidayalarm.mobile.push.firebase.domain;

import com.google.firebase.database.PropertyName;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class User {

	private String deviceToken;

	private List<Long> favorites;

	//	@PropertyName("device_token")
	public String getDeviceToken() {
		return deviceToken;
	}

	//	@PropertyName("device_token")
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@PropertyName("favorites")
	public List<Long> getFavorites() {
		return favorites;
	}

	@PropertyName("favorites")
	public void setFavorites(
			List<Long> favorites) {
		this.favorites = favorites;
	}
}
