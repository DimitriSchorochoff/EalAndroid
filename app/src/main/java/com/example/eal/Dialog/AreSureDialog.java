package com.example.eal.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.eal.databinding.DialogAreSureBinding;

public class AreSureDialog extends DialogFragment {
    Listener listener;

    protected AreSureDialog() {
        // Empty constructor required for DialogFragment
    }

    public static AreSureDialog newInstance(Listener listener) {
        AreSureDialog fragment = new AreSureDialog();
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogAreSureBinding binding = DialogAreSureBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());

        binding.dialogAreSureTextViewMessage.setText(listener.getDialogMessage());

        binding.dialogAreSureButtonYes.setOnClickListener(view->{
            listener.onYesAction();
            AreSureDialog.this.getDialog().cancel();
        });

        binding.dialogAreSureButtonNo.setOnClickListener(view->{
            AreSureDialog.this.getDialog().cancel();
        });

        binding.dialogAreSureImageViewQuit.setOnClickListener(view->{
            AreSureDialog.this.getDialog().cancel();
        });

        return builder.create();
    }

    public interface Listener{
        String getDialogMessage();
        void onYesAction();
    }
}
