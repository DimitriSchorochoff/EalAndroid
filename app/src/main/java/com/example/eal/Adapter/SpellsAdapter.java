package com.example.eal.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Activity.InvocationActivity;
import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Activity.Play.PlayPuppetEalardActivity;
import com.example.eal.Activity.Squad.EditEalardActivity;
import com.example.eal.Activity.SpellActivity;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEalard;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Element.Blood;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.R;

import java.util.ArrayList;

public class SpellsAdapter extends RecyclerView.Adapter<SpellsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> displayedSpells;
    private ArrayList<String> ownedSpells;
    private ArrayList<String> masteryAvailableSpellList;
    private Ealard editedEalard;
    private SpellAdapterMode mode;

    private Dialog dialog;

    private Invocation invocationCaller;
    private String spellNameCaller;

    //INGAME && INGAME INVOCATION
    private int playerPosition;
    private InGameEntity owner;

    //INGAME INVOCATION
    private int entityPosition;

    private EditEalardActivity editEalardActivity;

    public SpellsAdapter(Context context, ArrayList<String> displayedSpells, ArrayList<String> ownedSpells, Ealard editedEalard, SpellAdapterMode mode){
        this.mode = mode;

        this.context = context;
        this.displayedSpells = displayedSpells;
        this.ownedSpells = ownedSpells;
        majAvailableSpellList();
        this.editedEalard = editedEalard;
    }

    public SpellsAdapter(Context context, ArrayList<String> displayedSpells, Ealard editedEalard, Dialog dialog){
        this.mode = SpellAdapterMode.EDITEALARDDIALOG;

        this.context = context;
        this.displayedSpells = displayedSpells;
        this.ownedSpells = editedEalard.getSpellList();
        majAvailableSpellList();
        this.editedEalard = editedEalard;

        this.dialog = dialog;
    }

    public SpellsAdapter(Context context, Ealard editedEalard, Invocation invocationCaller, String spellNameCaller){
        this.mode = SpellAdapterMode.INVOCATION;

        this.context = context;
        this.displayedSpells = invocationCaller.getSpellList();
        this.editedEalard = editedEalard;

        this.spellNameCaller = spellNameCaller;
        this.invocationCaller = invocationCaller;
    }

    public SpellsAdapter(Context context, int playerPos, InGameEntity owner){
        this.mode = SpellAdapterMode.INGAME;

        this.context = context;
        this.playerPosition = playerPos;
        this.owner = owner;
        if(owner.getInGameSquad().isOnTurn()){
            this.displayedSpells = owner.getSpellList();
        } else{
            this.displayedSpells = owner.getSpellListShown();
        }
    }

    public SpellsAdapter(PlayPuppetEalardActivity context, ArrayList<String> displayedSpells, int playerPos, InGameEalard inGameEalard, Dialog dialog){
        this.mode = SpellAdapterMode.PUPPETDIALOG;

        this.context = context;
        this.playerPosition = playerPos;
        this.owner = inGameEalard;
        this.displayedSpells = displayedSpells;
        this.dialog = dialog;
        this.editedEalard = inGameEalard.getEalard();
        this.ownedSpells = this.editedEalard.getSpellList();
        majAvailableSpellList();
    }

    public SpellsAdapter(Context context, int playerPos, int entityPos, Invocation invocationCaller, String spellNameCaller){
        this.mode = SpellAdapterMode.INGAME_INVOCATION;

        this.context = context;
        this.displayedSpells = invocationCaller.getSpellList();
        this.playerPosition = playerPos;
        this.entityPosition = entityPos;

        this.invocationCaller = invocationCaller;
        this.spellNameCaller = spellNameCaller;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout mainLayout;

        private ImageButton action_button;
        private TextView name;
        private TextView power;
        private ImageView effect;
        private TextView energy;
        private TextView element;
        private ImageView mastery;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.cell_spell_main_layout);

            action_button = itemView.findViewById(R.id.cell_spell_imageButton_action);
            name = itemView.findViewById(R.id.cell_spell_textView_name);
            power = itemView.findViewById(R.id.cell_spell_textView_power);
            effect = itemView.findViewById(R.id.cell_spell_imageView_effect);
            energy = itemView.findViewById(R.id.cell_spell_textView_energy);
            element = itemView.findViewById(R.id.cell_spell_textView_element);
            mastery = itemView.findViewById(R.id.cell_spell_mastery_imageView);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_spell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Spell spell = Spell.getSpell(displayedSpells.get(position));

        switch (mode){
            case EDITEALARD:
                editEalardActivity = (EditEalardActivity) context;
                holder.itemView.setOnClickListener(view->{
                    Intent intent = new Intent(editEalardActivity, SpellActivity.class);
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.EALARD);
                    intent.putExtra("SpellName", spell.getSpellName());
                    editEalardActivity.updateEalard();
                    intent.putExtra("Ealard", editedEalard);
                    intent.putExtra(SpellActivity.EXTRA_IN_ADD_SPELL_DIALOG, false);
                    editEalardActivity.startActivity(intent);
                });

                holder.itemView.setOnLongClickListener(view->{
                    if(holder.action_button.getVisibility() == View.VISIBLE) {
                        holder.action_button.setVisibility(View.GONE);
                    }
                    else {
                        holder.action_button.setVisibility(View.VISIBLE);
                    }
                    return true;
                });

                holder.action_button.setImageResource(R.drawable.ic_delete);
                holder.action_button.setOnClickListener(view->{
                    holder.action_button.setVisibility(View.GONE); //Otherwise if we add the spell again delete will show
                    editEalardActivity.e.getSpellList().remove(position);

                    //Ensure we remove mastery spell that aren't available anymore

                    ArrayList<String> spellCountingForMastery = Spell.filterByRemovingHighestMasteryLevel(editEalardActivity.e.getSpellList());
                    ArrayList<String> filteredByMastery = Spell.filterByMastery(editEalardActivity.e.getSpellList(), spellCountingForMastery);
                    editEalardActivity.e.getSpellList().removeAll(editEalardActivity.e.getSpellList());
                    editEalardActivity.e.getSpellList().addAll(filteredByMastery);
                    majAvailableSpellList();

                    notifyDataSetChanged();
                    editEalardActivity.updateEssence();
                });
                break;


            case EDITEALARDDIALOG:
                editEalardActivity = (EditEalardActivity) context;
                holder.itemView.setOnClickListener(view->{
                    Intent intent = new Intent(editEalardActivity, SpellActivity.class);
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.EALARD);
                    intent.putExtra("SpellName", spell.getSpellName());
                    intent.putExtra(SpellActivity.EXTRA_IN_ADD_SPELL_DIALOG, true);
                    editEalardActivity.updateEalard();
                    intent.putExtra("Ealard", editedEalard);
                    editEalardActivity.startActivity(intent);
                });


                if(masteryAvailableSpellList.contains(spell.getSpellName()))
                    holder.action_button.setVisibility(View.VISIBLE);
                else
                    holder.action_button.setVisibility(View.GONE);
                holder.action_button.setImageResource(R.drawable.ic_add);
                holder.action_button.setOnClickListener(v -> {
                    editEalardActivity.e.getSpellList().add(displayedSpells.get(position));
                    editEalardActivity.sortList();
                    editEalardActivity.updateEssence();

                    //Quit if no essence left
                    if(editEalardActivity.getEssence() <= 0) dialog.dismiss();
                    else {
                        displayedSpells.remove(position);
                        majAvailableSpellList();
                        notifyDataSetChanged();
                    }
                });
                break;
            case INVOCATION:
                holder.itemView.setOnClickListener(view->{
                    InvocationActivity activity = (InvocationActivity) context;

                    Intent intent = new Intent(context, SpellActivity.class);
                    intent.putExtra("SpellName", spell.getSpellName());
                    intent.putExtra("Ealard", editedEalard);
                    intent.putExtra(SpellActivity.EXTRA_IN_ADD_SPELL_DIALOG, activity.isInAddSpellDialog());
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.INVOCATION);
                    intent.putExtra("InvocationCaller", invocationCaller);
                    intent.putExtra("SpellNameCaller", spellNameCaller);

                    context.startActivity(intent);
                });
                break;

            case CONSULT:
                holder.itemView.setOnClickListener(view->{
                    Intent intent = new Intent(context, SpellActivity.class);
                    intent.putExtra("SpellName", spell.getSpellName());
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.CONSULT);
                    context.startActivity(intent);
                });
                break;

            case INGAME:
                spell.setOwner(owner);

                AppCompatActivity activity = (AppCompatActivity) context;

                holder.itemView.setOnClickListener(v->{
                    Intent intent = new Intent(context, SpellActivity.class);
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.INGAME);
                    intent.putExtra("SpellName", spell.getSpellName());

                    intent.putExtra(SpellActivity.EXTRA_PLAYER_POSITION, playerPosition);
                    Player owner = PlayInGameActivity.getCurrentGamePlayers().get(playerPosition);
                    intent.putExtra(SpellActivity.EXTRA_ENTITY_POSITION, owner.getOwnedInGameSquad().getInGameEntities().indexOf(this.owner));
                    context.startActivity(intent);
                });

                if (!owner.isOnTurn()){
                    holder.mainLayout.setBackgroundColor(context.getColor(R.color.small_restriction));

                    holder.itemView.setOnLongClickListener(view->{
                        Toast.makeText(context, "Entity can't cast spell out of turn", Toast.LENGTH_SHORT).show();
                        return true;
                    });

                } else if(spell.getEnergy_cost() > owner.getEnergyCurrent()){
                    holder.mainLayout.setBackgroundColor(context.getColor(R.color.normal_restriction));

                    holder.itemView.setOnLongClickListener(view->{
                        Toast.makeText(context, "This entity doesn't have enough energy", Toast.LENGTH_SHORT).show();
                        return true;
                    });

                } else if (owner.getAlreadyCastedSpell().contains(spell.getSpellName())){
                    holder.itemView.setOnLongClickListener(view->{
                        Toast.makeText(context, "This entity already casted that spell this turn", Toast.LENGTH_SHORT).show();
                        return true;
                    });

                } else{
                    if(spell.getElement() instanceof Blood && !Blood.hasMadeBloodSacrifice(owner))
                        holder.mainLayout.setBackgroundColor(context.getColor(R.color.fire));
                    else
                        holder.mainLayout.setBackgroundColor(context.getColor(R.color.cell_background_color));

                    holder.itemView.setOnLongClickListener(view->{
                        spell.cast(activity, owner, PlayInGameActivity.getCurrentGamePlayers(), PlayInGameActivity.getCurrentGameTurnManager());
                        return true;});
                }

                //We ensure alreadyCasted spell color is set no matter what
                if(owner.getAlreadyCastedSpell().contains(spell.getSpellName()))
                    holder.mainLayout.setBackgroundColor(context.getColor(R.color.large_restriction));

                break;

            case INGAME_INVOCATION:
                spell.setOwner(owner);

                holder.itemView.setOnClickListener(view->{
                    Intent intent = new Intent(context, SpellActivity.class);
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.INGAME_INVOCATION);
                    intent.putExtra("SpellName", spell.getSpellName());
                    intent.putExtra(SpellActivity.EXTRA_PLAYER_POSITION, playerPosition);
                    intent.putExtra(SpellActivity.EXTRA_ENTITY_POSITION, entityPosition);
                    intent.putExtra("InvocationCaller", invocationCaller);
                    intent.putExtra("SpellNameCaller", spellNameCaller);

                    context.startActivity(intent);
                });
                break;

            case PUPPETDIALOG:
                PlayPuppetEalardActivity puppetEalardActivity = (PlayPuppetEalardActivity) context;

                holder.itemView.setOnClickListener(view->{
                    Intent intent = new Intent(context, SpellActivity.class);
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.INGAME);
                    intent.putExtra("SpellName", spell.getSpellName());

                    intent.putExtra(SpellActivity.EXTRA_PLAYER_POSITION, playerPosition);
                    Player owner = PlayInGameActivity.getCurrentGamePlayers().get(playerPosition);
                    intent.putExtra(SpellActivity.EXTRA_ENTITY_POSITION, owner.getOwnedInGameSquad().getInGameEntities().indexOf(this.owner));
                    context.startActivity(intent);
                });

                if(masteryAvailableSpellList.contains(spell.getSpellName()))
                    holder.action_button.setVisibility(View.VISIBLE);
                else
                    holder.action_button.setVisibility(View.GONE);
                holder.action_button.setImageResource(R.drawable.ic_add);

                holder.action_button.setOnClickListener(view->{
                    if(editedEalard.addSpell(spell.getSpellName())){
                        puppetEalardActivity.getSpellsAdapter().notifyDataSetChanged();
                        puppetEalardActivity.majValue();
                    } else{
                        Toast.makeText(context, "Out of essence", Toast.LENGTH_SHORT).show();
                    }

                    //Quit if no essence left
                    if(editedEalard.getEssence() <= 0) dialog.dismiss();
                    else {
                        displayedSpells.remove(position);
                        majAvailableSpellList();
                        notifyDataSetChanged();
                    }
                });
                break;
        }

        holder.name.setText(spell.getSpellName());
        holder.power.setText(Integer.toString(spell.getPower()));

        holder.effect.setImageDrawable(Spell.getEffectImage(spell.getEffect()));

        holder.energy.setText(Integer.toString(spell.getEnergy_cost()));

        holder.element.setBackgroundColor(Spell.getElementColor(spell.getElementName()));

        if(spell.getMasteryLevel() > 0)
            holder.mastery.setVisibility(View.VISIBLE);
        else
            holder.mastery.setVisibility(View.GONE);
    }

    public void majAvailableSpellList(){
        masteryAvailableSpellList = Spell.filterByMastery(Spell.getListAllEalardSpell(), ownedSpells);
    }

    @Override
    public int getItemCount() {
        return displayedSpells.size();
    }

    public enum SpellAdapterMode{
        EDITEALARD,
        EDITEALARDDIALOG,
        CONSULT,
        INVOCATION,
        INGAME,
        INGAME_INVOCATION,
        PUPPETDIALOG
    }
}
