package com.example.eal.Dialog.AddSpellDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.eal.Adapter.SpellsAdapter;

public class AddSpellDialogEditEalard extends AddSpellDialog {
    protected AddSpellDialogEditEalard(){

    }

    public static AddSpellDialogEditEalard newInstance(Listener listener) {
        AddSpellDialogEditEalard fragment = new AddSpellDialogEditEalard();
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        this.dialogAdapter = new SpellsAdapter(getContext(), availableSpellList, editedEalard, dialog);

        setDialog();

        return dialog;
    }
}
