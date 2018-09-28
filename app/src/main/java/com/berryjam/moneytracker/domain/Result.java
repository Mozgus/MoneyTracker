package com.berryjam.moneytracker.domain;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return TextUtils.equals(status, "success");
    }

    @NonNull
    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                '}';
    }

}
