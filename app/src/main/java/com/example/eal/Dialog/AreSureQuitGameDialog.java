package com.example.eal.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.eal.Activity.MainMenu;
import com.example.eal.databinding.DialogAreSureBinding;

public class AreSureQuitGameDialog extends AreSureDialog implements AreSureDialog.Listener{

    protected AreSureQuitGameDialog() {
        // Empty constructor required for DialogFragment
    }


    public static AreSureQuitGameDialog newInstance() {
        AreSureQuitGameDialog fragment = new AreSureQuitGameDialog();
        fragment.listener = fragment;
        return fragment;
    }

    //Listener Interface
    @Override
    public String getDialogMessage() {
        return "Current game won't be saved !";
    }

    @Override
    public void onYesAction() {
        getContext().startActivity(new Intent(getContext(), MainMenu.class));
    }
}
