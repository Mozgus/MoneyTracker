package com.berryjam.moneytracker.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.berryjam.moneytracker.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView itemName;
    private TextView itemPrice;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
    }

    public void bind(final Item item, final ItemsAdapterListener listener, final int position, boolean selected) {
        itemName.setText(item.getName());
        itemPrice.setText(String.valueOf(item.getPrice()));


        itemView.setSelected(selected);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onItemClick(item, position);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != listener) {
                    listener.onItemLongClick(item, position);
                }
                return true;
            }
        });

    }

}
