package com.example.eal.Dialog.AddSpellDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eal.Adapter.SpellsAdapter;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Class.Spell.SpellComparator;
import com.example.eal.databinding.DialogAddSpellBinding;

import java.util.ArrayList;

public class AddSpellDialog extends DialogFragment {
    protected Listener listener;
    protected SpellsAdapter dialogAdapter;

    protected Ealard editedEalard;
    protected ArrayList<String> availableSpellList;
    protected DialogAddSpellBinding binding;
    protected Dialog dialog;

    private SpellComparator.CompareType currentCompareType;
    private boolean isSortAsc;


    protected AddSpellDialog(){
        //Need to be empty
    }

    public static AddSpellDialog newInstance(Listener listener, SpellsAdapter dialogAdapter) {
        AddSpellDialog fragment = new AddSpellDialog();
        fragment.listener = listener;
        fragment.dialogAdapter = dialogAdapter;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        binding = DialogAddSpellBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());
        dialog = builder.create();

        editedEalard = listener.getEditedEalard();
        availableSpellList = Spell.getListAllEalardSpell();
        for(String spell: editedEalard.getSpellList()) availableSpellList.remove(spell);

        return dialog;
    }

    protected void setDialog(){
        setDefaultSort(availableSpellList);

        binding.dialogSpellImageViewQuit.setOnClickListener(view->dialog.dismiss());


        binding.dialogSpellRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.dialogSpellRecycler.setAdapter(dialogAdapter);

        setSorter();
    }

    private void setDefaultSort(ArrayList<String> availableSpellList){
        currentCompareType = listener.getDefaultType();

        SpellComparator.sortByCompareType(availableSpellList, currentCompareType, listener.isAscByDefault());
        isSortAsc = listener.isAscByDefault();
    }

    private void setSorter(){
        binding.dialogSpellNameSorter.setOnClickListener(view->{
            if(currentCompareType != SpellComparator.CompareType.NAME){
                isSortAsc = true;
                currentCompareType = SpellComparator.CompareType.NAME;
            }
            else
                isSortAsc = !isSortAsc;

            SpellComparator.sortByCompareType(availableSpellList, currentCompareType, isSortAsc);
            dialogAdapter.notifyDataSetChanged();
        });

        binding.dialogSpellEffectSorter.setOnClickListener(view->{
            if(currentCompareType != SpellComparator.CompareType.EFFECT){
                isSortAsc = true;
                currentCompareType = SpellComparator.CompareType.EFFECT;
            }
            else
                isSortAsc = !isSortAsc;

            SpellComparator.sortByCompareType(availableSpellList, currentCompareType, isSortAsc);
            dialogAdapter.notifyDataSetChanged();
        });

        binding.dialogSpellEnergySorter.setOnClickListener(view->{
            if(currentCompareType != SpellComparator.CompareType.ENERGY){
                isSortAsc = true;
                currentCompareType = SpellComparator.CompareType.ENERGY;
            }
            else
                isSortAsc = !isSortAsc;

            SpellComparator.sortByCompareType(availableSpellList, currentCompareType, isSortAsc);
            dialogAdapter.notifyDataSetChanged();
        });

        binding.dialogSpellElementSorter.setOnClickListener(view->{
            if(currentCompareType != SpellComparator.CompareType.ELEMENT){
                isSortAsc = true;
                currentCompareType = SpellComparator.CompareType.ELEMENT;
            }
            else
                isSortAsc = !isSortAsc;

            SpellComparator.sortByCompareType(availableSpellList, currentCompareType, isSortAsc);
            dialogAdapter.notifyDataSetChanged();
        });
    }


    public interface Listener{
        Ealard getEditedEalard();
        SpellComparator.CompareType getDefaultType();
        boolean isAscByDefault();
    }
}
