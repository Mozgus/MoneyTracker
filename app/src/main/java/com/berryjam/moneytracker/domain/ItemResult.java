package com.berryjam.moneytracker.domain;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ItemResult extends Result {

    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemResult{" +
                "id=" + id +
                '}';
    }

}
