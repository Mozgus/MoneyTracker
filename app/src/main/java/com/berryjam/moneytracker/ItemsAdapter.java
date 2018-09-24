package com.berryjam.moneytracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<Item> items = Collections.emptyList();
    private SparseBooleanArray selections = new SparseBooleanArray();
    private ItemsAdapterListener listener = null;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        Item item = items.get(position);
        itemViewHolder.bind(item, listener, position, selections.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        items.add(0, item);
        notifyItemInserted(0);
    }

    public void remove(int pos) {
        items.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setItemsAdapterListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }

    public void toggleItem(int position) {
        if (selections.get(position, false)) {
            selections.delete(position);
        } else {
            selections.put(position, true);
        }
        notifyItemChanged(position);
    }

    public List<Integer> getSelectedItems() {
        List<Integer> selected = new ArrayList<>(selections.size());
        for (int i = 0; i < selections.size(); i++) {
            selected.add(selections.keyAt(i));
        }
        return selected;
    }

    public int getSelectedItemCount() {
        return selections.size();
    }

    public void clearSelections() {
        selections.clear();
        notifyDataSetChanged();
    }

}
