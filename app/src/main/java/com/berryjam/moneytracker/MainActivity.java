package com.berryjam.moneytracker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    MainPagesAdapter mainPagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        mainPagesAdapter = new MainPagesAdapter(getSupportFragmentManager(), this);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mainPagesAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
