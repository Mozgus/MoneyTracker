package com.berryjam.moneytracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berryjam.moneytracker.dummy.DummyData;

public class ItemsFragment extends Fragment {
    public static final String KEY_TYPE = "type";
    public static final int TYPE_INCOMES = 1;
    public static final int TYPE_EXPENSES = 2;

    public static ItemsFragment newInstance(int type) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ItemsFragment.KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ItemsFragment() {
        // Required empty public constructor
    }

    private ItemsAdapter adapter;
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            type = args.getInt(KEY_TYPE);
        }
        adapter = new ItemsAdapter();
        adapter.setItems(DummyData.getDummyItems());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

}
