package com.berryjam.moneytracker.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berryjam.moneytracker.App;
import com.berryjam.moneytracker.R;
import com.berryjam.moneytracker.domain.Api;
import com.berryjam.moneytracker.domain.BalanceResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {

    public static BalanceFragment newInstance() {
        return new BalanceFragment();
    }

    TextView incomeSum;
    TextView balance;
    TextView expenseSum;
    DiagramView diagram;
    private Api api;

    public BalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();
        if (null != activity) {
            api = ((App) activity.getApplication()).getApi();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        balance = view.findViewById(R.id.total);
        expenseSum = view.findViewById(R.id.expenseSum);
        incomeSum = view.findViewById(R.id.incomeSum);
        diagram = view.findViewById(R.id.diagram_view);

        updateData();
    }

    private void updateData() {
        Call<BalanceResult> call = api.balance();
        call.enqueue(new Callback<BalanceResult>() {
            @Override
            public void onResponse(@NonNull Call<BalanceResult> call, @NonNull Response<BalanceResult> response) {
                BalanceResult result = response.body();
                if (result != null && result.isSuccess()) {
                    incomeSum.setText(String.valueOf(result.getTotalIncome()));
                    expenseSum.setText(String.valueOf(result.getTotalExpenses()));
                    balance.setText(String.valueOf(result.getTotalIncome() - result.getTotalExpenses()));
                    diagram.updateBalance(result.getTotalIncome(), result.getTotalExpenses());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BalanceResult> call, @NonNull Throwable t) {
            }

        });

    }

}
