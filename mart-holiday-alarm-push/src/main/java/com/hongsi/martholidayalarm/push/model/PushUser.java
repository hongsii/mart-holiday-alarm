package com.hongsi.martholidayalarm.push.model;

import com.google.firebase.database.PropertyName;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(of = "deviceToken")
@ToString
public class PushUser {

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
}
