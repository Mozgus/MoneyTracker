package com.berryjam.moneytracker.main;

interface ItemsAdapterListener {
    void onItemClick(Item item, int position);

    void onItemLongClick(Item item, int position);
}
