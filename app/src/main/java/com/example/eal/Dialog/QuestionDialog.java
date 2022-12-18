package com.example.eal.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.eal.databinding.DialogAreSureBinding;
import com.example.eal.databinding.DialogQuestionBinding;

public class QuestionDialog extends DialogFragment {
    Listener listener;

    protected QuestionDialog() {
        // Empty constructor required for DialogFragment
    }

    public static QuestionDialog newInstance(Listener listener) {
        QuestionDialog fragment = new QuestionDialog();
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogQuestionBinding binding = DialogQuestionBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());

        binding.dialogQuestionTV.setText(listener.getDialogQuestion());

        binding.dialogQuestionButtonYes.setOnClickListener(view->{
            listener.onYesAction();
            QuestionDialog.this.getDialog().cancel();
        });

        binding.dialogQuestionButtonNo.setOnClickListener(view->{
            listener.onNoAction();
            QuestionDialog.this.getDialog().cancel();
        });

        binding.dialogQuestionImageViewQuit.setOnClickListener(view->{
            listener.onCancelAction();
            QuestionDialog.this.getDialog().cancel();
        });

        return builder.create();
    }

    public interface Listener{
        String getDialogQuestion();
        void onYesAction();
        void onNoAction();
        void onCancelAction();
    }
}
