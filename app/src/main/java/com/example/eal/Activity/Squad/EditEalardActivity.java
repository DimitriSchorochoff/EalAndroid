package com.example.eal.Activity.Squad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eal.Adapter.SpellsAdapter;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.Spell.SpellComparator;
import com.example.eal.Database.DBManager;
import com.example.eal.AdditionnalRessource.AdditionalFunction;
import com.example.eal.Dialog.AddSpellDialog.AddSpellDialog;
import com.example.eal.Dialog.AddSpellDialog.AddSpellDialogEditEalard;
import com.example.eal.Dialog.AreSureDialog;
import com.example.eal.R;
import com.example.eal.databinding.ActivityEditEalardBinding;
import com.example.eal.databinding.DialogAreSureBinding;

import java.util.ArrayList;

public class EditEalardActivity extends AppCompatActivity implements AreSureDialog.Listener, AddSpellDialog.Listener {
    public static String EXTRA_IN_ADD_SPELL_DIALOG = "InAddSpellDialog";

    private ActivityEditEalardBinding binding;
    public DBManager dbManager;
    public SpellsAdapter adapter;

    public Ealard e = null;

    private ArrayList<String> usedEalardNames;
    private ArrayList<String> previousSpellList;

    //We use the same following values for the dialog comparators
    private SpellComparator.CompareType currentCompareType;
    private boolean isSortAsc;
    private static final SpellComparator.CompareType defaultCompareType = SpellComparator.CompareType.ELEMENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditEalardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);


        dbManager = new DBManager(this);
        dbManager.open();

        String IDSquad = getIntent().getStringExtra("IDSquad");
        String IDEalard = getIntent().getStringExtra("IDEalard");

        e = (Ealard) getIntent().getSerializableExtra("Ealard");
        if(e!= null){}
        else if (IDSquad != null){
            usedEalardNames = dbManager.getFromSquadEalardsNames(IDSquad);
            e = new Ealard(dbManager, IDSquad, AdditionalFunction.getNextValidNumberedName(usedEalardNames, Ealard.GENERIC_EALARD_NAME));
        }
        else if (IDEalard != null){
            e = dbManager.getEalard(IDEalard);
        }
        else Log.d("MyError", "No extra given to EalardActivity");

        IDSquad = e.getiDSquad();
        usedEalardNames = dbManager.getFromSquadEalardsNames(IDSquad);
        previousSpellList = (ArrayList<String>) e.getSpellList().clone();


        setToolbar();

        usedEalardNames = dbManager.getFromSquadEalardsNames(IDSquad);
        binding.ealardName.setText(e.getName());
        binding.ealardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newName = binding.ealardName.getText().toString();
                if(!newName.equals(e.getName()) && usedEalardNames.contains(newName))
                    binding.ealardName.setBackgroundColor(App.getContext().getResources().getColor(R.color.invalid));
                else
                    binding.ealardName.setBackgroundColor(App.getContext().getColor(R.color.white));
            }
        });

        binding.ealardEssenceCurrent.setText(Integer.toString(e.getEssence()));

        binding.ealardProgressBarEssence.setMax(e.getEssenceMax());
        binding.ealardProgressBarEssence.setProgress(e.getEssence());


        binding.ealardVitalityMax.setText(Integer.toString(Ealard.getVitalityMax(e.getVitality())));
        binding.ealardEnergyMax.setText(Integer.toString(Ealard.getEnergyMax(e.getEnergy())));
        binding.ealardMobilityMax.setText(Integer.toString(Ealard.getMobilityMax(e.getMobility())));


        binding.ealardEditTextVitality.setText(Integer.toString(e.getVitality()));
        binding.ealardEditTextEnergy.setText(Integer.toString(e.getEnergy()));
        binding.ealardEditTextMobility.setText(Integer.toString(e.getMobility()));


        binding.ealardEditTextVitality.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    try {
                        int vitality = getVitality();

                        binding.ealardEditTextVitality.setText("0");
                        updateEssence();
                        int essenceDispo = getEssence();

                        if (vitality > essenceDispo) {
                            //Set vita to 0 to compute the maximum essence available
                            binding.ealardEditTextVitality.setText(Integer.toString(essenceDispo));
                        } else if (vitality < 0)
                            binding.ealardEditTextVitality.setText("0");
                        else
                            binding.ealardEditTextVitality.setText(Integer.toString(vitality));

                        updateEssence();
                        updateVitalityMax();
                    } catch (NumberFormatException e){
                        binding.ealardEditTextVitality.setText("0");
                        updateEssence();
                        updateVitalityMax();
                    }
                }
            }
        });

        binding.ealardPlusVitality.setOnClickListener(view->{
            clearAllFocus();
            
            int essence = getEssence();
            if (essence - Ealard.getCost_vitality() < 0){
                Toast.makeText(this, "Not enough essence", Toast.LENGTH_SHORT).show();
                return;
            }

            int prevVitality = getVitality();
            binding.ealardEditTextVitality.setText(Integer.toString(prevVitality + 1));

            updateEssence();
            updateVitalityMax();
        });

        binding.ealardMinusVitality.setOnClickListener(view->{
            clearAllFocus();

            int prevVitality = getVitality();
            if(prevVitality - 1 < 0)
                return; // Nothing happen

            binding.ealardEditTextVitality.setText(Integer.toString(prevVitality - 1));

            updateEssence();
            updateVitalityMax();
        });


        binding.ealardEditTextEnergy.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    try {
                        int energy = getEnergy();
                        binding.ealardEditTextEnergy.setText("0");
                        updateEssence();
                        int essenceDispo = getEssence();

                        if (energy > essenceDispo) {
                            binding.ealardEditTextEnergy.setText(Integer.toString(essenceDispo));
                        } else if (energy < 0)
                            binding.ealardEditTextEnergy.setText("0");
                        else
                            binding.ealardEditTextEnergy.setText(Integer.toString(energy));

                        updateEssence();
                        updateEnergyMax();
                    } catch (NumberFormatException e){
                        binding.ealardEditTextEnergy.setText("0");
                        updateEssence();
                        updateEnergyMax();
                    }
                }
            }
        });

        binding.ealardPlusEnergy.setOnClickListener(view->{
            clearAllFocus();

            int essence = getEssence();
            if (essence - Ealard.getCost_energy() < 0){
                Toast.makeText(this, "Not enough essence", Toast.LENGTH_SHORT).show();
                return;
            }

            int prevEnergy = getEnergy();
            binding.ealardEditTextEnergy.setText(Integer.toString(prevEnergy + 1));

            updateEssence();
            updateEnergyMax();
        });

        binding.ealardMinusEnergy.setOnClickListener(view->{
            clearAllFocus();

            int prevEnergy = getEnergy();
            if(prevEnergy - 1 < 0)
                return; // Nothing happen

            binding.ealardEditTextEnergy.setText(Integer.toString(prevEnergy - 1));

            updateEssence();
            updateEnergyMax();
        });


        binding.ealardEditTextMobility.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    try {
                        int mobility = getMobility();
                        binding.ealardEditTextMobility.setText("0");
                        updateEssence();
                        int essenceDispo = getEssence();

                        if (mobility > essenceDispo) {
                            binding.ealardEditTextMobility.setText(Integer.toString(essenceDispo));
                        } else if (mobility < 0)
                            binding.ealardEditTextMobility.setText("0");
                        else
                            binding.ealardEditTextMobility.setText(Integer.toString(mobility));

                        updateEssence();
                        updateMobilityMax();
                    } catch (NumberFormatException e){
                        binding.ealardEditTextMobility.setText("0");
                        updateEssence();
                        updateMobilityMax();
                    }
                }
            }
        });

        binding.ealardPlusMobility.setOnClickListener(view->{
            clearAllFocus();

            int essence = getEssence();
            if (essence - Ealard.getCost_mobility() < 0){
                Toast.makeText(this, "Not enough essence", Toast.LENGTH_SHORT).show();
                return;
            }

            int prevMobility = getMobility();
            binding.ealardEditTextMobility.setText(Integer.toString(prevMobility + 1));

            updateEssence();
            updateMobilityMax();
        });

        binding.ealardMinusMobility.setOnClickListener(view->{
            clearAllFocus();

            int prevMobility = getMobility();
            if(prevMobility - 1 < 0)
                return; // Nothing happen

            binding.ealardEditTextMobility.setText(Integer.toString(prevMobility - 1));

            updateEssence();
            updateMobilityMax();
        });

        binding.ealardSpellrecycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SpellsAdapter(this, e.getSpellList(), e.getSpellList(), e, SpellsAdapter.SpellAdapterMode.EDITEALARD);
        binding.ealardSpellrecycler.setAdapter(adapter);

        setSorter();

        binding.ealardImageButtonAddSpell.setOnClickListener(view-> {
            clearAllFocus();
            if(getEssence() < Ealard.getCost_spell()){
                Toast.makeText(this, "Out of essence", Toast.LENGTH_SHORT).show();
            } else {
                add_spell_dialog();
            }
        });


        binding.ealardButtonSave.setOnClickListener(view->{
            clearAllFocus();

            if(getEssence() > 0)
                still_essence_sure_dialog();
            else {
                save_change();
                back_function();
            }

        });

        //To ensure essence done
        updateEssence();

        if(getIntent().getBooleanExtra(EXTRA_IN_ADD_SPELL_DIALOG, false) && getEssence() >= Ealard.getCost_spell())
            add_spell_dialog();
    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText("Ealard editor");

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v-> {
            clearAllFocus();
            //Sort previousSpellList to match currentSpellList order
            SpellComparator.sortByCompareType(previousSpellList, currentCompareType, isSortAsc);
            //Check if no change have been done
            if(getEssence() == e.getEssence() &&
            getVitality() == e.getVitality() &&
            getEnergy() == e.getEnergy() &&
            getMobility() == e.getMobility() &&
                    previousSpellList.equals(e.getSpellList())){
                back_function();
            } else {
                back_dialog();
            }
        });

        ImageView save_icon = findViewById(R.id.tool_bar_save_icon);
        save_icon.setVisibility(View.VISIBLE);
        save_icon.setOnClickListener(v->{
            clearAllFocus();

            Ealard.setBufferIDEalard(e.getiDEalard());
            Toast.makeText(this, "Ealard saved in the buffer", Toast.LENGTH_SHORT).show();
        });

        ImageView import_icon = findViewById(R.id.tool_bar_import_icon);
        import_icon.setVisibility(View.VISIBLE);
        import_icon.setOnClickListener(v->import_ealard());
    }

    public void sortList(){
        SpellComparator.sortByCompareType(e.getSpellList(), currentCompareType, isSortAsc);
        adapter.notifyDataSetChanged();
    }

    private void setSorter(){
        currentCompareType = defaultCompareType;
        isSortAsc = true;

        SpellComparator.sortByCompareType(e.getSpellList(), currentCompareType, isSortAsc);

        binding.ealardNameSorter.setOnClickListener(view->{
            if(currentCompareType != SpellComparator.CompareType.NAME){
                isSortAsc = true;
                currentCompareType = SpellComparator.CompareType.NAME;
            }
            else
                isSortAsc = !isSortAsc;

            SpellComparator.sortByCompareType(e.getSpellList(), currentCompareType, isSortAsc);
            adapter.notifyDataSetChanged();
        });

        binding.ealardEffectSorter.setOnClickListener(view->{
            if(currentCompareType != SpellComparator.CompareType.EFFECT){
                isSortAsc = true;
                currentCompareType = SpellComparator.CompareType.EFFECT;
            }
            else
                isSortAsc = !isSortAsc;

            SpellComparator.sortByCompareType(e.getSpellList(), currentCompareType, isSortAsc);
            adapter.notifyDataSetChanged();
        });

        binding.ealardEnergySorter.setOnClickListener(view->{
            if(currentCompareType != SpellComparator.CompareType.ENERGY){
                isSortAsc = true;
                currentCompareType = SpellComparator.CompareType.ENERGY;
            }
            else
                isSortAsc = !isSortAsc;

            SpellComparator.sortByCompareType(e.getSpellList(), currentCompareType, isSortAsc);
            adapter.notifyDataSetChanged();
        });

        binding.ealardElementSorter.setOnClickListener(view->{
            if(currentCompareType != SpellComparator.CompareType.ELEMENT){
                isSortAsc = true;
                currentCompareType = SpellComparator.CompareType.ELEMENT;
            }
            else
                isSortAsc = !isSortAsc;

            SpellComparator.sortByCompareType(e.getSpellList(), currentCompareType, isSortAsc);
            adapter.notifyDataSetChanged();
        });
    }

    private void import_ealard(){
        clearAllFocus();

        if(Ealard.getBufferIDEalard() != null) {
            Ealard ealardToCopy = dbManager.getEalard(Ealard.getBufferIDEalard());

            binding.ealardName.setText(AdditionalFunction.getNextValidNumberedName(usedEalardNames, ealardToCopy.getName()));
            setEssence(ealardToCopy.getEssence());
            setVitality(ealardToCopy.getVitality());
            setEnergy(ealardToCopy.getEnergy());
            setMobility(ealardToCopy.getMobility());

            e.getSpellList().removeAll(e.getSpellList());
            e.getSpellList().addAll(ealardToCopy.getSpellList());
            adapter.notifyDataSetChanged();

            updateEssence();
            updateVitalityMax();
            updateEnergyMax();
            updateMobilityMax();

            Toast.makeText(this, "Ealard successfully copied", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "The buffer is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void back_dialog(){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        DialogAreSureBinding bindingBack = DialogAreSureBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(bindingBack.getRoot());

        AlertDialog dialog = dialogBuilder.create();

        bindingBack.dialogAreSureTextViewMessage.setText("Your changes won't be saved");

        bindingBack.dialogAreSureButtonYes.setOnClickListener(view->{
            back_function();
        });

        bindingBack.dialogAreSureButtonNo.setOnClickListener(view->{
            dialog.dismiss();
        });

        bindingBack.dialogAreSureImageViewQuit.setOnClickListener(view->{
            dialog.dismiss();
        });

        dialog.show();
    }

    private void still_essence_sure_dialog(){
        AreSureDialog essenceSureDialog = AreSureDialog.newInstance(this);
        essenceSureDialog.show(getSupportFragmentManager(), null);
    }

    //AreSureListener interface implementation
    @Override
    public String getDialogMessage() {
        return "You have some essences left";
    }

    @Override
    public void onYesAction() {
        save_change();
        back_function();
    }

    private void add_spell_dialog(){
        AddSpellDialog addSpellDialog = AddSpellDialogEditEalard.newInstance(this);
        addSpellDialog.show(getSupportFragmentManager(), null);
    }

    //AddSpellListener implementation
    @Override
    public Ealard getEditedEalard() {
        return e;
    }

    @Override
    public SpellComparator.CompareType getDefaultType() {
        return currentCompareType;
    }

    @Override
    public boolean isAscByDefault() {
        return isSortAsc;
    }

    private void back_function(){
        Intent intent = new Intent(this, EditSquadActivity.class);
        intent.putExtra("IDSquad", e.getiDSquad());

        this.startActivity(intent);
    }

    private void save_change(){
        updateEalard();
        if(dbManager.isEalardInDatabase(e.getiDEalard()))
            dbManager.editEalard(e);
        else
            dbManager.addEalard(e);
    }

    private void clearAllFocus(){
        AdditionalFunction.hideKeyboard(this);

        binding.ealardName.clearFocus();
        binding.ealardEditTextVitality.clearFocus();
        binding.ealardEditTextEnergy.clearFocus();
        binding.ealardEditTextMobility.clearFocus();
    }

    public int getEssence(){
        return Integer.parseInt(binding.ealardEssenceCurrent.getText().toString());
    }

    public int getVitality(){
        return Integer.parseInt(binding.ealardEditTextVitality.getText().toString());
    }

    public int getEnergy(){
        return Integer.parseInt(binding.ealardEditTextEnergy.getText().toString());
    }

    public int getMobility(){
        return Integer.parseInt(binding.ealardEditTextMobility.getText().toString());
    }

    public void setEssence(int essence){
        binding.ealardEssenceCurrent.setText(Integer.toString(essence));
    }

    public void setVitality(int vitality){
        binding.ealardEditTextVitality.setText(Integer.toString(vitality));
    }

    public void setEnergy(int energy){
        binding.ealardEditTextEnergy.setText(Integer.toString(energy));
    }

    public void setMobility(int mobility){
        binding.ealardEditTextMobility.setText(Integer.toString(mobility));
    }

    public void updateEalard(){
        String newEalardName = binding.ealardName.getText().toString();
        if(!newEalardName.equals(e.getName()))
            newEalardName = AdditionalFunction.getNextValidNumberedName(usedEalardNames, newEalardName);
        e.setName(newEalardName);

        this.e.setVitality(getVitality());
        this.e.setEnergy(getEnergy());
        this.e.setMobility(getMobility());
    }

    public void updateEssence(){
        int essence = e.getEssenceMax() -
                Integer.parseInt(binding.ealardEditTextVitality.getText().toString()) * Ealard.getCost_vitality() -
                Integer.parseInt(binding.ealardEditTextEnergy.getText().toString()) * Ealard.getCost_energy() -
                Integer.parseInt(binding.ealardEditTextMobility.getText().toString()) * Ealard.getCost_mobility() -
                e.getSpellList().size() * Ealard.getCost_spell();

        //Prevent player from clicking on add spell when there out of essence
        if(essence <= 0){
            binding.ealardImageButtonAddSpell.setVisibility(View.INVISIBLE);
        } else
            binding.ealardImageButtonAddSpell.setVisibility(View.VISIBLE);

        binding.ealardEssenceCurrent.setText(Integer.toString(essence));
        binding.ealardProgressBarEssence.setProgress(essence);
    }

    public void updateVitalityMax(){
        int vitality = Integer.parseInt(binding.ealardEditTextVitality.getText().toString());
        binding.ealardVitalityMax.setText(Integer.toString(e.getBase_vitality() + vitality * Ealard.getGain_vitality()));
    }

    public void updateEnergyMax(){
        int energy = Integer.parseInt(binding.ealardEditTextEnergy.getText().toString());
        binding.ealardEnergyMax.setText(Integer.toString(e.getBase_energy() + energy * Ealard.getGain_energy()));
    }

    public void updateMobilityMax(){
        int mobility = Integer.parseInt(binding.ealardEditTextMobility.getText().toString());
        binding.ealardMobilityMax.setText(Integer.toString(e.getBase_mobility() + mobility * Ealard.getGain_mobility()));
    }
}