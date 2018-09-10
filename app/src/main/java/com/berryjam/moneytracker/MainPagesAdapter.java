package com.berryjam.moneytracker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagesAdapter extends FragmentPagerAdapter {

    static final int PAGE_INCOMES = 0;
    static final int PAGE_EXPENSES = 1;
    static final int PAGE_BALANCE = 2;
    private static final int PAGES_COUNT = 3;
    private String[] pagesTitles;

    MainPagesAdapter(FragmentManager fm, Context context) {
        super(fm);
        pagesTitles = context.getResources().getStringArray(R.array.main_tabs);
    }

    @Override
    public Fragment getItem(int page) {
        switch (page) {
            case PAGE_INCOMES:
                return ItemsFragment.newInstance(Item.TYPE_INCOME);
            case PAGE_EXPENSES:
                return ItemsFragment.newInstance(Item.TYPE_EXPENSE);
            case PAGE_BALANCE:
                return BalanceFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pagesTitles[position];
    }

}
