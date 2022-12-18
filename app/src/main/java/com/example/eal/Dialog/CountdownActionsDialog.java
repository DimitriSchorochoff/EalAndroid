package com.example.eal.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eal.Class.TurnManager.CountdownAction;
import com.example.eal.Adapter.CountdownActionsAdapter;
import com.example.eal.databinding.DialogCountdownActionsBinding;

import java.util.ArrayList;

public class CountdownActionsDialog extends DialogFragment {
    private  Listener listener;
    private ArrayList<CountdownAction> countdownActions;

    protected CountdownActionsDialog() {
        // Empty constructor required for DialogFragment
    }

    public static CountdownActionsDialog newInstance(Listener listener) {
        CountdownActionsDialog fragment = new CountdownActionsDialog();
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogCountdownActionsBinding binding = DialogCountdownActionsBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());

        countdownActions = listener.getCountdownActions();

        binding.dialogCountdownActionImageViewQuit.setOnClickListener(view->{
            CountdownActionsDialog.this.getDialog().cancel();
        });

        if(countdownActions.isEmpty()){
            binding.dialogCountdownActionRecycler.setVisibility(View.GONE);
            binding.dialogCountdownActionNoItemTextView.setVisibility(View.VISIBLE);
        } else {
            binding.dialogCountdownActionRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            CountdownActionsAdapter adapter = new CountdownActionsAdapter(countdownActions);
            binding.dialogCountdownActionRecycler.setAdapter(adapter);
        }

        return builder.create();
    }

    public interface Listener{
        ArrayList<CountdownAction> getCountdownActions();
    }
}
