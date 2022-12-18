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
import com.example.eal.Class.Entities.Entity;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.SpellComparator;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayEntityBinding;

import java.util.Collections;

public class PlayEntityActivity extends AppCompatActivity {
    public static final String EXTRA_PLAYER_POSITION = "PlayerPosition";
    public static final String EXTRA_ENTITY_POSITION = "EntityPosition";

    private ActivityPlayEntityBinding binding;


    private int playerPosition;
    private Player player;
    private int inGameEntityPosition;
    private InGameEntity inGameEntity;
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
        binding = ActivityPlayEntityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        playerPosition = getIntent().getIntExtra(EXTRA_PLAYER_POSITION,-1);
        player = PlayInGameActivity.getCurrentGamePlayers().get(playerPosition);

        inGameEntityPosition = getIntent().getIntExtra(EXTRA_ENTITY_POSITION, -1);
        inGameEntity = player.getOwnedInGameSquad().getInGameEntities()
                .get(inGameEntityPosition);

        isSquadOnTurn = player.getOwnedInGameSquad().isOnTurn();

        if(inGameEntity.getVitalityMax() == 0){
            binding.activityPlayEntityVitalityImageView.setVisibility(View.GONE);
            binding.activityPlayEntityVitalityTextView.setVisibility(View.GONE);
            binding.activityPlayEntityVitalityTV.setVisibility(View.GONE);
        }

        if(inGameEntity.isOnTurn()){
            binding.activityPlayEntityMobilityUse.setOnClickListener(view->{
                if (inGameEntity.getMobilityCurrent() > 0) {
                    inGameEntity.movementUseMobility(1);
                } else {
                    Toast.makeText(this, "Out of mobility", Toast.LENGTH_SHORT).show();
                }

                majValue();
            });
        } else{
            binding.activityPlayEntityMobilityUse.setVisibility(View.GONE);
        }

        binding.activityPlayEntityRecyclerSpell.setLayoutManager(new LinearLayoutManager(this));
        spellsAdapter = new SpellsAdapter(this, playerPosition, inGameEntity);
        binding.activityPlayEntityRecyclerSpell.setAdapter(spellsAdapter);


        if((isSquadOnTurn || inGameEntity instanceof InGameInvocation) && inGameEntity.getSpellList().size() == 0){
            binding.activityPlayEntityMessageRecyclerTextView.setVisibility(View.VISIBLE);
            binding.activityPlayEntityMessageRecyclerTextView.setText(String.format("%s doesn't have any spell.", inGameEntity.getName()));
        } else if(!isSquadOnTurn && inGameEntity.getSpellListShown().size() == 0) {
            binding.activityPlayEntityMessageRecyclerTextView.setVisibility(View.VISIBLE);
            binding.activityPlayEntityMessageRecyclerTextView.setText("No spell have been shown yet");
        }

        if(inGameEntity.isOnTurn()){
            binding.activityPlayEntityTurnButton.setText("End turn");
            binding.activityPlayEntityTurnButton.setOnClickListener(v->{
                inGameEntity.endTurn();
            });
        } else if(!player.getOwnedInGameSquad().hasActiveEntity() && isSquadOnTurn && !inGameEntity.isDoneForRound()){
            binding.activityPlayEntityTurnButton.setText("Start turn");
            binding.activityPlayEntityTurnButton.setOnClickListener(v->{
                inGameEntity.startTurn();
                Intent intent = new Intent(this, PlayEntityActivity.class);
                intent.putExtra(PlayEntityActivity.EXTRA_PLAYER_POSITION, playerPosition);
                intent.putExtra(PlayEntityActivity.EXTRA_ENTITY_POSITION, inGameEntityPosition);
                startActivity(intent);
            });
        } else{
            binding.activityPlayEntityTurnButton.setVisibility(View.GONE);
        }


        currentCompareType = null;
        setSortAscTrue();

        binding.activityPlayEntityNameSorter.setOnClickListener(view->{
            if(isNameSortAsc){
                Collections.sort(inGameEntity.getSpellList(), SpellComparator.getCompareSpellByNameAsc());
                spellsAdapter.notifyDataSetChanged();

                currentCompareType = SpellComparator.CompareType.NAME;
                setSortAscTrue();
                isNameSortAsc = false;
            } else{
                Collections.sort(inGameEntity.getSpellList(), SpellComparator.getCompareSpellByNameDesc());
                spellsAdapter.notifyDataSetChanged();

                isNameSortAsc = true;
            }
        });

        binding.activityPlayEntityEffectSorter.setOnClickListener(view->{
            if(isEffectSortAsc){
                Collections.sort(inGameEntity.getSpellList(), SpellComparator.getCompareSpellByEffectAsc());
                spellsAdapter.notifyDataSetChanged();

                currentCompareType = SpellComparator.CompareType.EFFECT;
                setSortAscTrue();
                isEffectSortAsc = false;
            } else{
                Collections.sort(inGameEntity.getSpellList(), SpellComparator.getCompareSpellByEffectDesc());
                spellsAdapter.notifyDataSetChanged();

                isEffectSortAsc = true;
            }
        });

        binding.activityPlayEntityEnergySorter.setOnClickListener(view->{
            if(isEnergySortAsc){
                Collections.sort(inGameEntity.getSpellList(), SpellComparator.getCompareSpellByEnergyAsc());
                spellsAdapter.notifyDataSetChanged();

                currentCompareType = SpellComparator.CompareType.ENERGY;
                setSortAscTrue();
                isEnergySortAsc = false;
            } else{
                Collections.sort(inGameEntity.getSpellList(), SpellComparator.getCompareSpellByEnergyDesc());
                spellsAdapter.notifyDataSetChanged();

                isEnergySortAsc = true;
            }
        });

        binding.activityPlayEntityTypeSorter.setOnClickListener(view->{
            if(isElemSortAsc){
                Collections.sort(inGameEntity.getSpellList(), SpellComparator.getCompareSpellByElementAsc());
                spellsAdapter.notifyDataSetChanged();

                currentCompareType = SpellComparator.CompareType.ELEMENT;
                setSortAscTrue();
                isElemSortAsc = false;
            } else{
                Collections.sort(inGameEntity.getSpellList(), SpellComparator.getCompareSpellByElementDesc());
                spellsAdapter.notifyDataSetChanged();

                isElemSortAsc = true;
            }
        });

        setToolbar();
        majValue();
    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        String turnMessage;
        if (inGameEntity.isOnTurn())
            turnMessage = "on turn";
        else
            turnMessage = "off turn";

        title.setText(String.format("%s: %s", inGameEntity.getName(), turnMessage));

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v->{
            startActivity(new Intent(this, PlayInGameActivity.class));
        });
    }

    public void majValue(){
        if(!isSquadOnTurn && inGameEntity.getEntityType() == Entity.EntityType.EALARD){
            if(inGameEntity.getShieldShown() >0){
                binding.activityPlayEntityShieldDesc.setVisibility(View.VISIBLE);
                binding.activityPlayEntityShieldTextView.setVisibility(View.VISIBLE);
                binding.activityPlayEntityShieldImageView.setVisibility(View.VISIBLE);

                binding.activityPlayEntityShieldTextView.setText(
                        String.format("%d", inGameEntity.getShieldShown()));
            }

            binding.activityPlayEntityVitalityTextView.setText(
                    String.format("%d", inGameEntity.getDamageTaken()));
            binding.activityPlayEntityVitalityImageView.setImageResource(R.drawable.ic_damage_dealt_icon);

            binding.activityPlayEntityEnergyTextView.setText(
                    String.format("%d", inGameEntity.getEnergyShown()));

            binding.activityPlayEntityMobilityTextView.setText(
                    String.format("%d", inGameEntity.getMobilityShown()));
        } else{
            if(inGameEntity.getShieldCurrent() >0){
                binding.activityPlayEntityShieldDesc.setVisibility(View.VISIBLE);
                binding.activityPlayEntityShieldTextView.setVisibility(View.VISIBLE);
                binding.activityPlayEntityShieldImageView.setVisibility(View.VISIBLE);

                binding.activityPlayEntityShieldTextView.setText(
                        String.format("%d", inGameEntity.getShieldCurrent()));
            }

            if(inGameEntity.getVitalityMax() == -1)
                binding.activityPlayEntityVitalityTextView.setText(
                        String.format("%d", inGameEntity.getVitalityCurrent()));
            else
                binding.activityPlayEntityVitalityTextView.setText(
                    String.format("%d/%d", inGameEntity.getVitalityCurrent(), inGameEntity.getVitalityMax()));

            binding.activityPlayEntityEnergyTextView.setText(
                    String.format("%d/%d", inGameEntity.getEnergyCurrent(), inGameEntity.getEnergyMax()));

            binding.activityPlayEntityMobilityTextView.setText(
                    String.format("%d/%d", inGameEntity.getMobilityCurrent(), inGameEntity.getMobilityMax()));
        }

        spellsAdapter.notifyDataSetChanged();
    }

    private void setSortAscTrue(){
        isNameSortAsc = true;
        isEffectSortAsc = true;
        isEnergySortAsc = true;
        isElemSortAsc = true;
    }
}