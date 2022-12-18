package com.example.eal.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.eal.databinding.DialogHistoricBinding;
import com.example.eal.databinding.DialogMessageBinding;

public class HistoricDialog extends DialogFragment {
    protected Listener listener;
    DialogHistoricBinding binding;

    protected HistoricDialog(){}

    public static HistoricDialog newInstance(Listener listener) {
        HistoricDialog fragment = new HistoricDialog();
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        binding = DialogHistoricBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());

        binding.dialogHistoricImageViewQuit.setOnClickListener(v->quitDialog());
        binding.dialogHistoricButtonYes.setOnClickListener(v->quitDialog());

        binding.dialogHistoricTitleTV.setText(listener.getTitle());
        binding.dialogHistoricTextViewMessage.setText(listener.getHistoric());

        return builder.create();
    }

    private void quitDialog(){
        listener.onQuitHistoricDialog();
        HistoricDialog.this.getDialog().cancel();
    }

    public interface Listener{
        String getTitle();
        String getHistoric();
        void onQuitHistoricDialog();
    }
}
