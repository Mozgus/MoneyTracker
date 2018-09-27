package com.berryjam.moneytracker.main;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    static final String TYPE_EXPENSE = "expense";
    static final String TYPE_INCOME = "income";
    private int id;
    private String name;
    private int price;
    private String type;

    Item(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Item(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    private Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readInt();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(type);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
