package com.example.eal.Dialog.AddSpellDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.eal.Activity.Play.PlayPuppetEalardActivity;
import com.example.eal.Adapter.SpellsAdapter;
import com.example.eal.Class.InGame.InGameEalard;

public class AddSpellDialogPuppetEalard extends AddSpellDialog {
    protected AddSpellDialogPuppetEalard(){

    }

    public static AddSpellDialogPuppetEalard newInstance(Listener listener) {
        AddSpellDialogPuppetEalard fragment = new AddSpellDialogPuppetEalard();
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Listener myListener = (Listener) listener;
        dialogAdapter = new SpellsAdapter(myListener.getPuppetActivity(), availableSpellList, myListener.getPlayerPosition(), myListener.getInGameEalard() ,dialog);

        setDialog();

        return dialog;
    }

    public interface Listener extends AddSpellDialog.Listener{
        PlayPuppetEalardActivity getPuppetActivity();
        int getPlayerPosition();
        InGameEalard getInGameEalard();
    }
}
