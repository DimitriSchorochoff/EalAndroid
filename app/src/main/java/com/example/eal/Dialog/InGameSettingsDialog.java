package com.example.eal.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.databinding.DialogAreSureBinding;
import com.example.eal.databinding.DialogSettingsInGameBinding;

public class InGameSettingsDialog extends DialogFragment {
    Listener listener;

    protected InGameSettingsDialog() {
        // Empty constructor required for DialogFragment
    }

    public static InGameSettingsDialog newInstance(Listener listener) {
        InGameSettingsDialog fragment = new InGameSettingsDialog();
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogSettingsInGameBinding binding = DialogSettingsInGameBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());

        Player activePlayer = listener.activePlayer();

        binding.dialogSettingsInGameQuit.setOnClickListener(view->{
            InGameSettingsDialog.this.getDialog().cancel();
        });

        binding.dialogSettingsInGameInformationDialog.setChecked(activePlayer.isShowInformationDialog());
        binding.dialogSettingsInGameInformationDialog.setOnClickListener(v->{
            activePlayer.setShowInformationDialog(binding.dialogSettingsInGameInformationDialog.isChecked());
        });

        binding.dialogSettingsInGameSurrenderButton.setOnClickListener(v->{
           activePlayer.surrender();
        });

        return builder.create();
    }

    public interface Listener{
        Player activePlayer();
    }
}
