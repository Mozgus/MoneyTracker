package com.berryjam.moneytracker.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.berryjam.moneytracker.App;
import com.berryjam.moneytracker.R;
import com.berryjam.moneytracker.add.AddActivity;
import com.berryjam.moneytracker.entry.EntryActivity;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 10;

    private App app;
    private ActionMode actionMode;

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
    protected void onResume() {
        super.onResume();
        app = (App) getApplication();
        if (!app.isLoggedIn()) {
            logout();
        } else {
            enableTabsContent();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        floatingActionButton.hide();
        tabLayout.setBackgroundColor(getResources().getColor(R.color.color_action_background));
        actionMode = mode;
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        floatingActionButton.show();
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        actionMode = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        logout();
        return true;
    }

    private void logout() {
        startActivity(new Intent(this, EntryActivity.class));
        app.deleteAuthToken();
        finish();
    }

    private void enableTabsContent() {
        mainPagesAdapter = new MainPagesAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mainPagesAdapter);
        viewPager.addOnPageChangeListener(new PageListener());
        tabLayout.setupWithViewPager(viewPager);
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
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                case ViewPager.SCROLL_STATE_SETTLING:
                    if (null != actionMode) actionMode.finish();
                    break;
            }
        }

    }

}
