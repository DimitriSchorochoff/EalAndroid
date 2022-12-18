package com.example.eal.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.eal.databinding.DialogMessageBinding;

public class MessageDialog extends DialogFragment {
    protected Listener listener;
    DialogMessageBinding binding;

    protected MessageDialog(){}

    public static MessageDialog newInstance(Listener listener) {
        MessageDialog fragment = new MessageDialog();
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        binding = DialogMessageBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());

        binding.dialogMessageImageViewQuit.setOnClickListener(v->quitDialog());
        binding.dialogMessageButtonYes.setOnClickListener(v->quitDialog());

        binding.dialogMessageTextViewMessage.setText(listener.getMessage());

        String additionalMessage = listener.getAdditionalMessage();
        if(additionalMessage != null){
            binding.dialogMessageTextViewMessageSupp.setVisibility(View.VISIBLE);
            binding.dialogMessageTextViewMessageSupp.setText(additionalMessage);
        }

        return builder.create();
    }

    private void quitDialog(){
        listener.onQuitMessageDialog();
        MessageDialog.this.getDialog().cancel();
    }

    public interface Listener{
        String getMessage();
        String getAdditionalMessage();
        void onQuitMessageDialog();
    }
}
