package com.berryjam.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    ItemsAdapter adapter = new ItemsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(createTestItems());
    }

    private List<Item> createTestItems() {
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
