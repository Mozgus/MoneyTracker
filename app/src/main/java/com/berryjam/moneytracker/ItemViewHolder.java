package com.berryjam.moneytracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView itemName;
    private TextView itemPrice;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
    }

    public void bind(Item item) {
        itemName.setText(item.getName());
        itemPrice.setText(item.getPrice());
    }

}
