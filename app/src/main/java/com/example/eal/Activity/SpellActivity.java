package com.example.eal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eal.Activity.Play.PlayEntityActivity;
import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Activity.Play.PlayPuppetEalardActivity;
import com.example.eal.Activity.Squad.EditEalardActivity;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.Entities.Entity;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEalard;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.R;
import com.example.eal.databinding.ActivitySpellBinding;

import java.io.Serializable;

public class SpellActivity extends AppCompatActivity {
    //EXTRA for inGameModes
    public static final String EXTRA_PLAYER_POSITION = "PlayerPosition";
    public static final String EXTRA_ENTITY_POSITION = "EntityPosition";

    //If consulted in add spell dialog
    public static final String EXTRA_IN_ADD_SPELL_DIALOG = "InAddSpellDialog";

    private ActivitySpellBinding binding;

    private SpellActivityMode mode;
    private Spell spell;
    private Ealard editedEalard;
    private boolean inAddSpellDialog;

    //INVOCATION && PLAYINGAME INVOCATION
    private Invocation invocationCaller;
    private String spellNameCaller;

    //PLAYINGAME && PLAYINGAME INVOCATION
    private int playerPosition;
    private int entityPosition;
    private Player player;
    private InGameEntity inGameEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        mode = (SpellActivityMode) getIntent().getSerializableExtra("Mode");
        spell = Spell.getSpell(getIntent().getStringExtra("SpellName"));
        editedEalard = (Ealard) getIntent().getSerializableExtra("Ealard");
        inAddSpellDialog = getIntent().getBooleanExtra(EXTRA_IN_ADD_SPELL_DIALOG, false);

        if(mode == SpellActivityMode.INVOCATION || mode == SpellActivityMode.INGAME_INVOCATION){
            invocationCaller = (Invocation) getIntent().getSerializableExtra("InvocationCaller");
            spellNameCaller = getIntent().getStringExtra("SpellNameCaller");
        }
        if(mode == SpellActivityMode.INGAME || mode == SpellActivityMode.INGAME_INVOCATION){
            playerPosition = getIntent().getIntExtra(EXTRA_PLAYER_POSITION, -1);
            player = PlayInGameActivity.getCurrentGamePlayers().get(playerPosition);

            entityPosition = getIntent().getIntExtra(EXTRA_ENTITY_POSITION, -1);
            inGameEntity = player.getOwnedInGameSquad().getInGameEntities().get(entityPosition);

            spell.setOwner(inGameEntity);
        }


        setToolbar();

        binding.spellActivityTextViewElement.setText(spell.getElementName());

        binding.spellActivityTextViewPower.setText(Integer.toString(spell.getPower()));
        binding.spellActivityImageViewEffect.setImageDrawable(Spell.getEffectImage(spell.getEffect()));

        binding.spellActivityTextViewEnergy.setText(Integer.toString(spell.getEnergy_cost()));

        binding.spellActivityRangeTV.setText(spell.getRange().getName());
        binding.spellActivityImageViewRange.setImageDrawable(spell.getRange().getAreaImage());

        binding.spellActivityAreaTV.setText(spell.getArea().getName());
        binding.spellActivityImageViewArea.setImageDrawable(spell.getArea().getAreaImage());

        binding.spellActivityDescription.setText(spell.getDescription());


        switch (spell.getEffect()){
            case GLYPHE:
                binding.spellActivityLink.setText("See glyphe statistics");
                binding.spellActivityLink.setVisibility(View.VISIBLE);
                break;

            case ETHERED:
                binding.spellActivityLink.setText("See ethered statistics");
                binding.spellActivityLink.setVisibility(View.VISIBLE);
                break;
        }
        binding.spellActivityLink.setOnClickListener(view->{
            Intent intent = new Intent(this, InvocationActivity.class);

            intent.putExtra("Invocation", spell.getInvocation());
            intent.putExtra("SpellName", spell.getSpellName());

            switch (mode){
                case CONSULT:
                    intent.putExtra("Mode", InvocationActivity.InvocationActivityMode.CONSULT);
                    break;
                case INVOCATION:
                    //No break same as ealard
                case EALARD:
                    intent.putExtra("Mode", InvocationActivity.InvocationActivityMode.SPELL_EDIT);
                    intent.putExtra("Ealard", editedEalard);
                    intent.putExtra(InvocationActivity.EXTRA_IN_ADD_SPELL_DIALOG, inAddSpellDialog);

                    break;
                case INGAME_INVOCATION:
                    //NoBreak same as InGame
                case INGAME:
                    intent.putExtra("Mode", InvocationActivity.InvocationActivityMode.IN_GAME);
                    intent.putExtra(InvocationActivity.EXTRA_PLAYER_POSITION, playerPosition);
                    intent.putExtra(InvocationActivity.EXTRA_ENTITY_POSITION, entityPosition);
                    break;
            }
            startActivity(intent);
        });
    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText(String.format("Spell: %s", spell.getSpellName()));

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        switch (mode){
            case CONSULT:
                back_icon.setOnClickListener(view->startActivity(new Intent(this, LibraryActivity.class)));
                break;


            case EALARD:
                back_icon.setOnClickListener(view->back_edited_ealard());

                if (! editedEalard.getSpellList().contains(spell.getSpellName())){
                    ImageView add_icon = findViewById(R.id.tool_bar_add_icon);
                    add_icon.setVisibility(View.VISIBLE);
                    add_icon.setOnClickListener(view->{
                        if(editedEalard.addSpell(spell.getSpellName()))
                            back_edited_ealard();
                        else
                            Toast.makeText(this, "No enough essence", Toast.LENGTH_SHORT).show();
                    });
                }
                break;


            case INVOCATION:
                back_icon.setOnClickListener(view->back_invocation());
                break;

            case INGAME:
                back_icon.setOnClickListener(v-> back_in_game());

                majIcon();

                //Puppet ealard add spell
                ImageView add_spell_icon = findViewById(R.id.tool_bar_add_icon);
                add_spell_icon.setOnClickListener(view->{
                    ((InGameEalard)inGameEntity).getEalard().addSpell(spell.getSpellName());
                    back_in_game();
                });

                if(inGameEntity.canCastSpell(spell)){
                    ImageView spell_cast_icon = findViewById(R.id.tool_bar_spell_cast_icon);
                    spell_cast_icon.setOnClickListener(v-> {
                        //Ensure we can only spell when we can
                        if(inGameEntity.canCastSpell(spell))
                            spell.cast(SpellActivity.this, inGameEntity, PlayInGameActivity.getCurrentGamePlayers(), PlayInGameActivity.getCurrentGameTurnManager());
                    });
                }
                break;
            case INGAME_INVOCATION:
                back_icon.setOnClickListener(view->back_in_game_invocation());
                break;

            default:
                Log.d("Error", "You should call SpellActivity with a mode");
        }
    }

    public void afterSpellCast(){
        majIcon();
        back_in_game();
    }

    public void majIcon(){
        ImageView add_spell_icon = findViewById(R.id.tool_bar_add_icon);
        ImageView spell_cast_icon = findViewById(R.id.tool_bar_spell_cast_icon);
        if(inGameEntity.isPuppet() &&
                inGameEntity.getEntityType() == Entity.EntityType.EALARD &&
                !inGameEntity.getSpellList().contains(spell.getSpellName())){
            add_spell_icon.setVisibility(View.VISIBLE);
        }
        else if(inGameEntity.canCastSpell(spell))
            spell_cast_icon.setVisibility(View.VISIBLE);
        else{
            add_spell_icon.setVisibility(View.GONE);
            spell_cast_icon.setVisibility(View.GONE);
        }
    }

    void back_invocation(){
        Intent intent = new Intent(this, InvocationActivity.class);
        intent.putExtra("Invocation", invocationCaller);
        intent.putExtra("SpellName", spellNameCaller);

        intent.putExtra("Mode", InvocationActivity.InvocationActivityMode.SPELL_EDIT);
        intent.putExtra("Ealard", editedEalard);
        intent.putExtra(InvocationActivity.EXTRA_IN_ADD_SPELL_DIALOG, inAddSpellDialog);

        startActivity(intent);
    }

    void back_edited_ealard(){
        Intent intent = new Intent(this, EditEalardActivity.class);
        intent.putExtra("Ealard", editedEalard);
        intent.putExtra(EditEalardActivity.EXTRA_IN_ADD_SPELL_DIALOG, inAddSpellDialog);
        startActivity(intent);
    }

    void back_in_game(){
        Intent intent;
        if(inGameEntity.isPuppet() && inGameEntity.getEntityType() == Entity.EntityType.EALARD)
            intent = new Intent(this, PlayPuppetEalardActivity.class);
        else if (! inGameEntity.isDead())
            intent = new Intent(this, PlayEntityActivity.class);
        else{
            intent = new Intent(this, PlayInGameActivity.class);
            startActivity(intent);
            return;
        }

        intent.putExtra(PlayEntityActivity.EXTRA_PLAYER_POSITION, playerPosition);
        intent.putExtra(PlayEntityActivity.EXTRA_ENTITY_POSITION, entityPosition);
        startActivity(intent);
    }

    void back_in_game_invocation(){
        Intent intent = new Intent(this, InvocationActivity.class);

        intent.putExtra("Invocation", invocationCaller);
        intent.putExtra("SpellName", spellNameCaller);

        intent.putExtra("Mode", InvocationActivity.InvocationActivityMode.IN_GAME);
        intent.putExtra(InvocationActivity.EXTRA_PLAYER_POSITION, playerPosition);
        intent.putExtra(InvocationActivity.EXTRA_ENTITY_POSITION, entityPosition);
        startActivity(intent);
    }

    public enum SpellActivityMode implements Serializable {
        INVOCATION,
        CONSULT,
        EALARD,
        INGAME,
        INGAME_INVOCATION
    }
}