package com.berryjam.moneytracker.domain;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AuthResult extends Result {

    @SerializedName("auth_token")
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @NonNull
    @Override
    public String toString() {
        return "AuthResult{" +
                "authToken='" + authToken + '\'' +
                '}';
    }

}
