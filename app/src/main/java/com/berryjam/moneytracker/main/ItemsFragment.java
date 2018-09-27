package com.berryjam.moneytracker.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.berryjam.moneytracker.add.AddActivity;
import com.berryjam.moneytracker.Api;
import com.berryjam.moneytracker.App;
import com.berryjam.moneytracker.R;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.berryjam.moneytracker.main.MainActivity.REQUEST_CODE;

public class ItemsFragment extends Fragment {
    public static final String KEY_TYPE = "type";

    public static ItemsFragment newInstance(String type) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ItemsFragment() {
        // Required empty public constructor
    }

    private ItemsAdapter adapter;
    private String type;
    private Api api;
    private ActionMode mode;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            type = args.getString(KEY_TYPE);
        }
        api = ((App) Objects.requireNonNull(getActivity()).getApplication()).getApi();
        adapter = new ItemsAdapter();
        adapter.setItemsAdapterListener(new ItemsAdapterListenerImpl());
        loadItems();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.light_green_a700),
                ContextCompat.getColor(requireContext(), R.color.dark_sky_blue),
                ContextCompat.getColor(requireContext(), R.color.orange_a400),
                ContextCompat.getColor(requireContext(), R.color.lightish_blue)
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Item item = data.getParcelableExtra(AddActivity.KEY_ITEM);
            if (item.getType().equals(type)) {
                adapter.addItem(item);
            }
        }
    }

    public void loadItems() {
        Call<List<Item>> call = api.getItems(type);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                swipeRefreshLayout.setRefreshing(false);
                List<Item> items = response.body();
                adapter.setItems(items);
            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void removeSelectedItem() {
        final List<Integer> selected = adapter.getSelectedItems();
        for (int i = selected.size() - 1; i >= 0; i--) {
            adapter.remove(selected.get(i));
        }
        mode.finish();
    }

    class ItemsAdapterListenerImpl implements ItemsAdapterListener {
        @Override
        public void onItemClick(Item item, int position) {
            if (mode == null) return;
            toggleItem(position);
        }

        @Override
        public void onItemLongClick(Item item, int position) {
            if (null != mode) return;
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (null != activity)
                activity.startSupportActionMode(new ActionModeCallback());
            toggleItem(position);
        }

        private void toggleItem(int position) {
            adapter.toggleItem(position);
            int count = adapter.getSelectedItemCount();
            if (count > 0) {
                mode.setTitle(String.format("%s %s",
                        String.valueOf(count), getString(R.string.selected)));
            } else {
                mode.finish();
            }
        }

    }

    class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            mode = actionMode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = new MenuInflater(requireContext());
            inflater.inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.menu_item_delete) {
                showConfirmationDialog();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            adapter.clearSelections();
            mode = null;
        }

        private void showConfirmationDialog() {
            ConfirmDeleteFragment dialog = new ConfirmDeleteFragment();
            assert getFragmentManager() != null;
            dialog.show(getFragmentManager(), null);
            dialog.setListener(new ConfirmDeleteFragment.Listener() {
                @Override
                public void onDeleteConfirmed() {
                    removeSelectedItem();
                }

                @Override
                public void onCancelConfirmed() {
                    mode.finish();
                }
            });
        }

    }

}
