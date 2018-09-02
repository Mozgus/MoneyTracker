package com.berryjam.moneytracker.dummy;

import com.berryjam.moneytracker.Item;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static List<Item> getDummyItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Баклажан", "70р"));
        items.add(new Item("Огурцы", "65р"));
        items.add(new Item("Помидоры", "149р"));
        items.add(new Item("Сыр", "350р"));
        items.add(new Item("Колбаса", "310р"));
        items.add(new Item("Хлеб", "65р"));
        items.add(new Item("Макароны", "19р"));
        items.add(new Item("Рис", "44р"));
        items.add(new Item("Арбуз", "180р"));
        items.add(new Item("Баклажан", "70р"));
        items.add(new Item("Огурцы", "49р"));
        items.add(new Item("Помидоры", "59р"));
        items.add(new Item("Сыр", "365р"));
        items.add(new Item("Колбаса", "315р"));
        items.add(new Item("Хлеб", "67р"));
        items.add(new Item("Макароны", "20р"));
        items.add(new Item("Рис", "45р"));
        items.add(new Item("Арбуз", "170р"));
        return items;
    }
}
