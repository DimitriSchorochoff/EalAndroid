package com.example.eal.Activity.Play;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eal.Adapter.SpellsAdapter;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.InGame.InGameEalard;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.SpellComparator;
import com.example.eal.Dialog.AddSpellDialog.AddSpellDialog;
import com.example.eal.Dialog.AddSpellDialog.AddSpellDialogPuppetEalard;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayPuppetEntityBinding;

import java.util.Collections;

public class PlayPuppetEalardActivity extends AppCompatActivity implements AddSpellDialogPuppetEalard.Listener{
    public static final String EXTRA_PLAYER_POSITION = "PlayerPosition";
    public static final String EXTRA_ENTITY_POSITION = "EntityPosition";

    private ActivityPlayPuppetEntityBinding binding;

    private int playerPosition;
    private Player player;
    private int inGameEntityPosition;
    private InGameEalard inGameEalard;
    private boolean isSquadOnTurn;

    private SpellsAdapter spellsAdapter;

    private SpellComparator.CompareType currentCompareType;
    private boolean isNameSortAsc;
    private boolean isEffectSortAsc;
    private boolean isEnergySortAsc;
    private boolean isElemSortAsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayPuppetEntityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        playerPosition = getIntent().getIntExtra(EXTRA_PLAYER_POSITION,-1);
        player = PlayInGameActivity.getCurrentGamePlayers().get(playerPosition);

        inGameEntityPosition = getIntent().getIntExtra(EXTRA_ENTITY_POSITION, -1);
        inGameEalard =  (InGameEalard) player.getOwnedInGameSquad()
                .getInGameEntities()
                .get(inGameEntityPosition);

        isSquadOnTurn = player.getOwnedInGameSquad().isOnTurn();

        binding.activityPlayPuppetEntityEssenceProgressBar.setMax(PlayInGameActivity.getCurrentGameGameMode().getNumberEssencePerEalard());

        binding.activityPlayPuppetEntityVitalityPlus.setOnClickListener(v->{
            if(inGameEalard.puppetIncVitality()){
                majValue();
            } else
                outOfEssenceToast();
        });
        binding.activityPlayPuppetEntityVitalityMinus.setOnClickListener(v->{
            if(inGameEalard.puppetDecVitality())
                majValue();
            else
                Toast.makeText(this, String.format("This puppet as shown to have at least %s vitality", inGameEalard.getVitalityShown()), Toast.LENGTH_SHORT).show();
        });
        binding.activityPlayPuppetEntityEnergyPlus.setOnClickListener(v->{
            if(inGameEalard.puppetIncEnergy()){
                majValue();
            } else
                outOfEssenceToast();
        });
        binding.activityPlayPuppetEntityEnergyMinus.setOnClickListener(v->{
            if(inGameEalard.puppetDecEnergy())
                majValue();
            else
                Toast.makeText(this, String.format("This puppet as shown to have at least %s energy", inGameEalard.getEnergyShown()), Toast.LENGTH_SHORT).show();
        });

        binding.activityPlayPuppetEntityMobilityPlus.setOnClickListener(v->{
            if(inGameEalard.getEalard().incMobility()){
                inGameEalard.setMobilityCurrent(inGameEalard.getMobilityCurrent()+Ealard.getGain_mobility());
                majValue();
            } else
                outOfEssenceToast();
        });
        binding.activityPlayPuppetEntityMobilityMinus.setOnClickListener(v->{
            int mobility = inGameEalard.getEalard().getMobility();
            if(mobility <= Ealard.getMobility(inGameEalard.getMobilityShown())){
                Toast.makeText(this, String.format("This puppet as shown to have at least %s mobility", inGameEalard.getMobilityShown()), Toast.LENGTH_SHORT).show();
            } else{
                inGameEalard.getEalard().setMobility(mobility-1);
                inGameEalard.setMobilityCurrent(inGameEalard.getMobilityCurrent()-Ealard.getGain_mobility());
                majValue();
            }
        });

        binding.activityPlayPuppetEntityRecyclerSpell.setLayoutManager(new LinearLayoutManager(this));
        spellsAdapter = new SpellsAdapter(this, playerPosition, inGameEalard);
        binding.activityPlayPuppetEntityRecyclerSpell.setAdapter(spellsAdapter);


        if(inGameEalard.isOnTurn()){
            binding.activityPlayPuppetEntityAddSpell.setOnClickListener(v->{
                add_spell_dialog();
            });

            binding.activityPlayPuppetEntityMobilityUse.setOnClickListener(view->{
                if (inGameEalard.getMobilityCurrent() > 0) {
                    inGameEalard.movementUseMobility(1);
                } else {
                    Toast.makeText(this, "Out of mobility", Toast.LENGTH_SHORT).show();
                }

                majValue();
            });
        } else{
            binding.activityPlayPuppetEntityAddSpell.setVisibility(View.GONE);
            binding.activityPlayPuppetEntityMobilityUse.setVisibility(View.GONE);
        }

        if(inGameEalard.isOnTurn()){
            binding.activityPlayPuppetEntityTurnButton.setText("End turn");
            binding.activityPlayPuppetEntityTurnButton.setOnClickListener(v->inGameEalard.endTurn());
        } else if(!player.getOwnedInGameSquad().hasActiveEntity() && isSquadOnTurn && !inGameEalard.isDoneForRound()){
            binding.activityPlayPuppetEntityTurnButton.setText("Start turn");
            binding.activityPlayPuppetEntityTurnButton.setOnClickListener(v->{
                inGameEalard.startTurn();
                Intent intent = new Intent(this, PlayPuppetEalardActivity.class);
                intent.putExtra(PlayEntityActivity.EXTRA_PLAYER_POSITION, playerPosition);
                intent.putExtra(PlayEntityActivity.EXTRA_ENTITY_POSITION, inGameEntityPosition);
                startActivity(intent);
            });
        } else{
            binding.activityPlayPuppetEntityTurnButton.setVisibility(View.GONE);
        }

        setSorter();
        majValue();
        setToolbar();
    }

    public void majValue(){
        if (inGameEalard.getVitalityCurrent() <= 0)
            inGameEalard.die();

        int numberEssence = Ealard.essenceLeft(PlayInGameActivity.getCurrentGameGameMode().getNumberEssencePerEalard(),
                inGameEalard.getEalard().getVitality(),
                inGameEalard.getEalard().getEnergy(),
                inGameEalard.getEalard().getMobility(),
                inGameEalard.getSpellList());
        binding.activityPlayPuppetEntityEssenceCurrent.setText(Integer.toString(numberEssence));
        binding.activityPlayPuppetEntityEssenceProgressBar.setProgress(numberEssence);

        if(inGameEalard.getShieldCurrent() >0){
            binding.activityPlayPuppetEntityShieldDesc.setVisibility(View.VISIBLE);
            binding.activityPlayPuppetEntityShieldTextView.setVisibility(View.VISIBLE);
            binding.activityPlayPuppetEntityShieldImageView.setVisibility(View.VISIBLE);

            binding.activityPlayPuppetEntityShieldTextView.setText(
                    String.format("%d", inGameEalard.getShieldCurrent()));
        }

        binding.activityPlayPuppetEntityVitalityTextView.setText(
                String.format("%d/%d", inGameEalard.getVitalityCurrent(), inGameEalard.getVitalityMax()));

        binding.activityPlayPuppetEntityEnergyTextView.setText(
                String.format("%d/%d", inGameEalard.getEnergyCurrent(), inGameEalard.getEnergyMax()));

        binding.activityPlayPuppetEntityMobilityTextView.setText(
                String.format("%d/%d", inGameEalard.getMobilityCurrent(), inGameEalard.getMobilityMax()));


        spellsAdapter.notifyDataSetChanged();
        if(inGameEalard.getSpellList().isEmpty()){
            binding.activityPlayPuppetEntityNoItemTextView.setVisibility(View.VISIBLE);
        } else {
            binding.activityPlayPuppetEntityNoItemTextView.setVisibility(View.GONE);
        }

        if(inGameEalard.getEalard().getEssence() < Ealard.getCost_spell() || !inGameEalard.isOnTurn())
            binding.activityPlayPuppetEntityAddSpell.setVisibility(View.INVISIBLE);
        else
            binding.activityPlayPuppetEntityAddSpell.setVisibility(View.VISIBLE);
    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        String turnMessage;
        if (inGameEalard.isOnTurn())
            turnMessage = "on turn";
        else
            turnMessage = "off turn";

        title.setText(String.format("%s: %s", inGameEalard.getName(), turnMessage));

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v->{
            startActivity(new Intent(this, PlayInGameActivity.class));
        });
    }

    private void setSorter(){
        currentCompareType = null;
        setSortAscTrue();

        binding.activityPlayPuppetEntityNameSorter.setOnClickListener(view->{
            if(isNameSortAsc){
                Collections.sort(inGameEalard.getSpellList(), SpellComparator.getCompareSpellByNameAsc());
                spellsAdapter.notifyDataSetChanged();

                currentCompareType = SpellComparator.CompareType.NAME;
                setSortAscTrue();
                isNameSortAsc = false;
            } else{
                Collections.sort(inGameEalard.getSpellList(), SpellComparator.getCompareSpellByNameDesc());
                spellsAdapter.notifyDataSetChanged();

                isNameSortAsc = true;
            }
        });

        binding.activityPlayPuppetEntityEffectSorter.setOnClickListener(view->{
            if(isEffectSortAsc){
                Collections.sort(inGameEalard.getSpellList(), SpellComparator.getCompareSpellByEffectAsc());
                spellsAdapter.notifyDataSetChanged();

                currentCompareType = SpellComparator.CompareType.EFFECT;
                setSortAscTrue();
                isEffectSortAsc = false;
            } else{
                Collections.sort(inGameEalard.getSpellList(), SpellComparator.getCompareSpellByEffectDesc());
                spellsAdapter.notifyDataSetChanged();

                isEffectSortAsc = true;
            }
        });

        binding.activityPlayPuppetEntityEnergySorter.setOnClickListener(view->{
            if(isEnergySortAsc){
                Collections.sort(inGameEalard.getSpellList(), SpellComparator.getCompareSpellByEnergyAsc());
                spellsAdapter.notifyDataSetChanged();

                currentCompareType = SpellComparator.CompareType.ENERGY;
                setSortAscTrue();
                isEnergySortAsc = false;
            } else{
                Collections.sort(inGameEalard.getSpellList(), SpellComparator.getCompareSpellByEnergyDesc());
                spellsAdapter.notifyDataSetChanged();

                isEnergySortAsc = true;
            }
        });

        binding.activityPlayPuppetEntityTypeSorter.setOnClickListener(view->{
            if(isElemSortAsc){
                Collections.sort(inGameEalard.getSpellList(), SpellComparator.getCompareSpellByElementAsc());
                spellsAdapter.notifyDataSetChanged();

                currentCompareType = SpellComparator.CompareType.ELEMENT;
                setSortAscTrue();
                isElemSortAsc = false;
            } else{
                Collections.sort(inGameEalard.getSpellList(), SpellComparator.getCompareSpellByElementDesc());
                spellsAdapter.notifyDataSetChanged();

                isElemSortAsc = true;
            }
        });
    }

    private void setSortAscTrue(){
        isNameSortAsc = true;
        isEffectSortAsc = true;
        isEnergySortAsc = true;
        isElemSortAsc = true;
    }

    private void outOfEssenceToast(){
        Toast.makeText(this, "This puppet is out of essence", Toast.LENGTH_SHORT).show();
    }

    public SpellsAdapter getSpellsAdapter() {
        return spellsAdapter;
    }

    private void add_spell_dialog(){
        AddSpellDialogPuppetEalard dialog = AddSpellDialogPuppetEalard.newInstance(this);
        dialog.show(getSupportFragmentManager(), null);
    }


    @Override
    public Ealard getEditedEalard() {
        return inGameEalard.getEalard();
    }

    @Override
    public SpellComparator.CompareType getDefaultType() {
        return currentCompareType;
    }

    @Override
    public boolean isAscByDefault() {
        return isEffectSortAsc && isElemSortAsc && isEnergySortAsc && isNameSortAsc;
    }

    @Override
    public PlayPuppetEalardActivity getPuppetActivity() {
        return this;
    }

    @Override
    public int getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public InGameEalard getInGameEalard() {
        return inGameEalard;
    }
}