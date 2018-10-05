package com.berryjam.moneytracker.domain;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class BalanceResult extends Result {
    @SerializedName("total_expenses")
    private long totalExpenses;

    @SerializedName("total_income")
    private long totalIncome;

    public long getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public long getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    @NonNull
    @Override
    public String toString() {
        return "BalanceResult{" +
                "totalExpenses=" + totalExpenses +
                ", totalIncome=" + totalIncome +
                '}';
    }

}
