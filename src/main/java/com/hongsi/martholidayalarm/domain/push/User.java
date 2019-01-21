package com.hongsi.martholidayalarm.domain.push;

import com.google.firebase.database.PropertyName;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class User {

	private String deviceToken;
	private List<Long> favorites;

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@PropertyName("favorites")
	public List<Long> getFavorites() {
		return favorites;
	}

	@PropertyName("favorites")
	public void setFavorites(List<Long> favorites) {
		this.favorites = favorites;
	}

	public List<TokenMessage> makeTokenMessages(List<PushMart> pushMarts) {
		return pushMarts.stream()
				.filter(mart -> hasSameMartId(mart))
				.map(pushMart -> TokenMessage.fromMart(deviceToken, pushMart))
				.collect(Collectors.toList());
	}

	private boolean hasSameMartId(PushMart mart) {
		return favorites.contains(mart.getId());
	}
}
