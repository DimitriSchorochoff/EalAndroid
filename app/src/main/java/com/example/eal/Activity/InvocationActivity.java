package com.example.eal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eal.Adapter.SpellsAdapter;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.R;
import com.example.eal.databinding.ActivityInvocationBinding;

import java.io.Serializable;

public class InvocationActivity extends AppCompatActivity {
    public static final String EXTRA_PLAYER_POSITION = "PlayerPosition";
    public static final String EXTRA_ENTITY_POSITION = "EntityPosition";

    public static final String EXTRA_IN_ADD_SPELL_DIALOG = "InAddSpellDialog";

    private ActivityInvocationBinding binding;
    private Invocation invocation;
    private String spellName;
    private Ealard editedEalard;
    private boolean inAddSpellDialog;
    private InvocationActivityMode mode;

    //INGAME
    private int playerPosition;
    private int entityPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        invocation = (Invocation) getIntent().getSerializableExtra("Invocation");
        spellName = getIntent().getStringExtra("SpellName");
        editedEalard = (Ealard) getIntent().getSerializableExtra("Ealard");
        inAddSpellDialog = getIntent().getBooleanExtra(EXTRA_IN_ADD_SPELL_DIALOG, false);
        mode = (InvocationActivityMode) getIntent().getSerializableExtra("Mode");

        if(mode == InvocationActivityMode.IN_GAME){
            playerPosition = getIntent().getIntExtra(EXTRA_PLAYER_POSITION, -1);
            entityPosition = getIntent().getIntExtra(EXTRA_ENTITY_POSITION, -1);
        }

        setToolbar();

        //Invocation type are spell effect
        binding.invocationTextViewType.setText(Spell.getEffectName(invocation.getType()));

        if(invocation.getBase_vitality() == 0){
            binding.invocationVitalityDesc.setVisibility(View.GONE);
            binding.invocationImageVitality.setVisibility(View.GONE);
            binding.invocationImageVitality.setVisibility(View.GONE);
        } else{
            binding.invocationTextViewVitality.setText(Integer.toString(invocation.getBase_vitality()));
        }

        binding.invocationTextViewEnergy.setText(Integer.toString(invocation.getBase_energy()));
        binding.invocationTextViewMobility.setText(Integer.toString(invocation.getBase_mobility()));


        if(invocation.getSpellList().size() == 0){
            binding.invocationConstraintLayoutSorter.setVisibility(View.GONE);
        } else{
            binding.invocationSpellrecycler.setLayoutManager(new LinearLayoutManager(this));
            SpellsAdapter adapter = null;
            switch (mode) {
                case CONSULT:
                    adapter = new SpellsAdapter(this, invocation.getSpellList(), invocation.getSpellList(), editedEalard, SpellsAdapter.SpellAdapterMode.CONSULT);
                    break;
                case SPELL_EDIT:
                    adapter = new SpellsAdapter(this, editedEalard, invocation, spellName);
                    break;
                case IN_GAME:
                    adapter = new SpellsAdapter(this, playerPosition, entityPosition, invocation, spellName);
                    break;
            }
            binding.invocationSpellrecycler.setAdapter(adapter);
        }


        if(invocation.getAdditionalInformation() == null){
            binding.invocationDescriptionDesc.setVisibility(View.GONE);
            binding.invocationDescription.setVisibility(View.GONE);
        } else{
            binding.invocationDescription.setText(invocation.getAdditionalInformation());
        }
    }

    void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText(String.format("Invocation: %s", invocation.getName()));

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);

        switch (mode){
            case CONSULT:
                back_icon.setOnClickListener(v->startActivity(new Intent(this, LibraryActivity.class)));
                break;

            case SPELL_EDIT:
                back_icon.setOnClickListener(view->{
                    Intent intent = new Intent(this, SpellActivity.class);
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.EALARD);
                    intent.putExtra("Ealard", editedEalard);
                    intent.putExtra(SpellActivity.EXTRA_IN_ADD_SPELL_DIALOG, inAddSpellDialog);
                    intent.putExtra("SpellName", spellName);

                    startActivity(intent);
                });
                break;
            case IN_GAME:
                back_icon.setOnClickListener(view->{
                    Intent intent = new Intent(this, SpellActivity.class);
                    intent.putExtra("Mode", SpellActivity.SpellActivityMode.INGAME);
                    intent.putExtra("SpellName", spellName);

                    intent.putExtra(SpellActivity.EXTRA_PLAYER_POSITION, playerPosition);
                    intent.putExtra(SpellActivity.EXTRA_ENTITY_POSITION, entityPosition);
                    startActivity(intent);
                });
                break;
        }
    }

    public boolean isInAddSpellDialog(){
        return inAddSpellDialog;
    }

    public enum InvocationActivityMode implements Serializable{
        CONSULT,
        SPELL_EDIT,
        IN_GAME
    }
}