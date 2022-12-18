package com.example.eal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Activity.Play.PlayEntityActivity;
import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Activity.Play.PlayPuppetEalardActivity;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Entity;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Dialog.TargetDialog;
import com.example.eal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class InGameEntitiesAdapter extends RecyclerView.Adapter<InGameEntitiesAdapter.ViewHolder> {
    private Context context;
    private Mode mode;

    private int positionPlayer;
    private Player player;
    private InGameSquad inGameSquad;
    private ArrayList<InGameEntity> displayedInGameEntities;

    //TARGETDIALOG
    private HashMap<InGameEntity, Integer> spellTargets;
    private TargetDialog dialog;

    public InGameEntitiesAdapter(Context context, int positionPlayer){
        this.context = context;
        this.mode = Mode.PLAYINGAMEACTIVITY;

        this.positionPlayer = positionPlayer;
        this.player = PlayInGameActivity.getCurrentGamePlayers().get(positionPlayer);
        this.inGameSquad = player.getOwnedInGameSquad();
        this.displayedInGameEntities = inGameSquad.getInGameEntities();
    }

    public InGameEntitiesAdapter(Context context, Player player, int positionPlayer, HashMap<InGameEntity, Integer> spellTargets, TargetDialog dialog, Predicate<InGameEntity> targetFilter){
        this.context = context;
        this.mode = Mode.TARGETDIALOG;

        this.positionPlayer = positionPlayer;
        this.player = player;
        this.inGameSquad = player.getOwnedInGameSquad();
        this.displayedInGameEntities = inGameSquad.getInGameEntitiesFiltered(targetFilter);

        this.spellTargets = spellTargets;
        this.dialog = dialog;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout mainLayout;

        private TextView name;

        private TextView shield_textView;
        private ImageView shield_imageView;

        private TextView vitality_textView;
        private ImageView vitality_imageView;

        private TextView energy_textView;
        private TextView mobility_textView;

        private RecyclerView element_recycler;

        //Target
        private ConstraintLayout targetedLayout;
        private TextView numberHitTv;
        private Button buttonPlus;
        private Button buttonMinus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.cell_entity_main_layout);

            name = itemView.findViewById(R.id.cell_entity_textView_name);

            shield_textView = itemView.findViewById(R.id.cell_entity_textView_shield);
            shield_imageView = itemView.findViewById(R.id.cell_entity_imageView_shield);

            vitality_textView = itemView.findViewById(R.id.cell_entity_textView_vitality);
            vitality_imageView = itemView.findViewById(R.id.cell_entity_imageView_vitality);
            energy_textView = itemView.findViewById(R.id.cell_entity_textView_energy);
            mobility_textView = itemView.findViewById(R.id.cell_entity_textView_mobility);

            element_recycler = itemView.findViewById(R.id.cell_entity_recycler_element);

            targetedLayout = itemView.findViewById(R.id.cell_entity_targeted_constraintLayout);
            numberHitTv = itemView.findViewById(R.id.cell_entity_targeted_number_hit_tV);
            buttonPlus = itemView.findViewById(R.id.cell_entity_targeted_plus);
            buttonMinus = itemView.findViewById(R.id.cell_entity_targeted_minus);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_entity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InGameEntity inGameEntity = displayedInGameEntities.get(position);

        setInGameEntityColor(inGameEntity, holder);


        holder.name.setText(inGameEntity.getName());

        if(inGameEntity.getEntityType() == Entity.EntityType.EALARD && inGameEntity.isPuppet()){
            if (inGameEntity.getShieldCurrent() > 0){
                holder.shield_textView.setVisibility(View.VISIBLE);
                holder.shield_imageView.setVisibility(View.VISIBLE);

                holder.shield_textView.setText(Integer.toString(inGameEntity.getShieldCurrent()));
            }

            holder.vitality_textView.setText(Integer.toString(inGameEntity.getDamageTaken()));
            holder.vitality_imageView.setImageResource(R.drawable.ic_damage_dealt_icon);
            holder.energy_textView.setText(Integer.toString(inGameEntity.getEnergyCurrent()));
            holder.mobility_textView.setText(Integer.toString(inGameEntity.getMobilityCurrent()));


            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, ElementsAdapter.numColumn);
            holder.element_recycler.setLayoutManager(layoutManager);

            ElementsAdapter elementsAdapter =
                    new ElementsAdapter(Spell.getSpellListElements(inGameEntity.getSpellList()));
            holder.element_recycler.setAdapter(elementsAdapter);
        } else if(inGameEntity.getEntityType() == Entity.EntityType.EALARD && !inGameSquad.isOnTurn()){
            if (inGameEntity.getShieldShown() > 0){
                holder.shield_textView.setVisibility(View.VISIBLE);
                holder.shield_imageView.setVisibility(View.VISIBLE);

                holder.shield_textView.setText(Integer.toString(inGameEntity.getShieldShown()));
            }
            holder.vitality_textView.setText(Integer.toString(inGameEntity.getDamageTaken()));
            holder.vitality_imageView.setImageResource(R.drawable.ic_damage_dealt_icon);

            holder.energy_textView.setText(Integer.toString(inGameEntity.getEnergyShown()));
            holder.mobility_textView.setText(Integer.toString(inGameEntity.getMobilityShown()));


            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, ElementsAdapter.numColumn);
            holder.element_recycler.setLayoutManager(layoutManager);

            ElementsAdapter elementsAdapter =
                    new ElementsAdapter(Spell.getSpellListElements(inGameEntity.getSpellListShown()));
            holder.element_recycler.setAdapter(elementsAdapter);
        } else{
            if (inGameEntity.getShieldCurrent() > 0){
                holder.shield_textView.setVisibility(View.VISIBLE);
                holder.shield_imageView.setVisibility(View.VISIBLE);

                holder.shield_textView.setText(Integer.toString(inGameEntity.getShieldCurrent()));
            }
            if(inGameEntity.getVitalityMax() == 0){
                holder.vitality_imageView.setVisibility(View.GONE);
                holder.vitality_textView.setVisibility(View.GONE);
            }
            holder.vitality_textView.setText(Integer.toString(inGameEntity.getVitalityCurrent()));
            holder.energy_textView.setText(Integer.toString(inGameEntity.getEnergyCurrent()));
            holder.mobility_textView.setText(Integer.toString(inGameEntity.getMobilityCurrent()));


            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, ElementsAdapter.numColumn);
            holder.element_recycler.setLayoutManager(layoutManager);

            ElementsAdapter elementsAdapter =
                    new ElementsAdapter(Spell.getSpellListElements(inGameEntity.getSpellList()));
            holder.element_recycler.setAdapter(elementsAdapter);
        }

        switch (mode){
            case PLAYINGAMEACTIVITY:
                holder.itemView.setOnClickListener(view->startPlayEntity(position, inGameEntity));

                if(inGameSquad.isOnTurn()){
                    holder.itemView.setOnLongClickListener(v->{
                        if(! inGameSquad.hasActiveEntity() && !inGameEntity.isDoneForRound()){
                            inGameEntity.startTurn();
                            startPlayEntity(position, inGameEntity);
                        } else{
                            Toast.makeText(context, "This squad already has an active entity", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    });
                } else{
                    holder.itemView.setOnLongClickListener(v->{
                        Toast.makeText(context, "It isn't this squad turn", Toast.LENGTH_SHORT).show();
                        return true;
                    });
                }
                break;

            case TARGETDIALOG:
                holder.numberHitTv.setText(Integer.toString(spellTargets.getOrDefault(inGameEntity, 0)));

                holder.buttonPlus.setOnClickListener(v->{
                    int numberHit = spellTargets.getOrDefault(inGameEntity, 0);

                    spellTargets.put(inGameEntity, numberHit+1);
                    holder.numberHitTv.setText(Integer.toString(spellTargets.getOrDefault(inGameEntity, 0)));
                });

                holder.buttonMinus.setOnClickListener(v->{
                    int numberHit = spellTargets.getOrDefault(inGameEntity, 0);

                    if(numberHit > 1){
                        spellTargets.put(inGameEntity, numberHit-1);
                        holder.numberHitTv.setText(Integer.toString(spellTargets.getOrDefault(inGameEntity, 0)));
                    }
                });

                holder.itemView.setOnClickListener(view->{
                    if(spellTargets.containsKey(inGameEntity)){
                        spellTargets.remove(inGameEntity);

                        if(dialog.isSpellMultiHit())
                            holder.targetedLayout.setVisibility(View.GONE);

                    } else{
                        spellTargets.put(inGameEntity, 1);

                        if (dialog.isSpellMultiHit())
                            holder.targetedLayout.setVisibility(View.VISIBLE);
                    }

                    setInGameEntityColor(inGameEntity, holder);
                    dialog.majValue();
                    notifyDataSetChanged();
                });
        }
    }

    @Override
    public int getItemCount() {
        return displayedInGameEntities.size();
    }

    public void setInGameEntityColor(InGameEntity inGameEntity, ViewHolder holder){
        if((mode == Mode.PLAYINGAMEACTIVITY && inGameEntity.isOnTurn()) || (mode == Mode.TARGETDIALOG && spellTargets.containsKey(inGameEntity))){
            holder.mainLayout.setBackgroundColor(App.getContext().getColor(R.color.selected));
        } else{
            if(inGameEntity.isDoneForRound())
                holder.mainLayout.setBackgroundColor(App.getContext().getColor(R.color.large_restriction));
            else
                holder.mainLayout.setBackgroundColor(App.getContext().getColor(R.color.cell_background_color));
        }
    }

    public void startPlayEntity(int positionInGameEntity, InGameEntity inGameEntity){

        if(player.isPuppetPlayer() && inGameEntity.getEntityType() == Entity.EntityType.EALARD){
            Intent intent = new Intent(context, PlayPuppetEalardActivity.class);
            intent.putExtra(PlayPuppetEalardActivity.EXTRA_PLAYER_POSITION, positionPlayer);
            intent.putExtra(PlayPuppetEalardActivity.EXTRA_ENTITY_POSITION, positionInGameEntity);
            context.startActivity(intent);
        } else{
            Intent intent = new Intent(context, PlayEntityActivity.class);
            intent.putExtra(PlayEntityActivity.EXTRA_PLAYER_POSITION, positionPlayer);
            intent.putExtra(PlayEntityActivity.EXTRA_ENTITY_POSITION, positionInGameEntity);
            context.startActivity(intent);
        }

    }

    enum Mode{
        PLAYINGAMEACTIVITY,
        TARGETDIALOG
    }
}
