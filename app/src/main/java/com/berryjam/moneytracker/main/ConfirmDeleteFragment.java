package com.berryjam.moneytracker.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.berryjam.moneytracker.R;

public class ConfirmDeleteFragment extends DialogFragment {
    private Listener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDeleteConfirmed();
                        }
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onCancelConfirmed();
                        }

                    }
                }).create();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onDeleteConfirmed();

        void onCancelConfirmed();
    }

}
