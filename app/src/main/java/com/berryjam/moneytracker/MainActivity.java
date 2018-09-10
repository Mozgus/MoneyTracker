package com.berryjam.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE = 10;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    MainPagesAdapter mainPagesAdapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        floatingActionButton = findViewById(R.id.fab);

        mainPagesAdapter = new MainPagesAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mainPagesAdapter);
        viewPager.addOnPageChangeListener(new PageListener());
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                if (viewPager.getCurrentItem() == MainPagesAdapter.PAGE_INCOMES) {
                    intent.putExtra(AddActivity.KEY_TYPE, Item.TYPE_INCOME);
                } else if (viewPager.getCurrentItem() == MainPagesAdapter.PAGE_EXPENSES) {
                    intent.putExtra(AddActivity.KEY_TYPE, Item.TYPE_EXPENSE);
                }
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    class PageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case MainPagesAdapter.PAGE_EXPENSES:
                case MainPagesAdapter.PAGE_INCOMES:
                    floatingActionButton.show();
                    break;
                case MainPagesAdapter.PAGE_BALANCE:
                    floatingActionButton.hide();
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

    }

}
