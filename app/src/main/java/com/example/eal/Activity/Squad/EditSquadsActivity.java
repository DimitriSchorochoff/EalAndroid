package com.example.eal.Activity.Squad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eal.Activity.MainMenu;
import com.example.eal.Adapter.SquadsAdapter;
import com.example.eal.Application.App;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.Squad;
import com.example.eal.Database.DBManager;
import com.example.eal.AdditionnalRessource.AdditionalFunction;
import com.example.eal.R;
import com.example.eal.databinding.ActivitySquadsBinding;

import java.util.ArrayList;

public class EditSquadsActivity extends AppCompatActivity {
    public static final String EXTRA_GAMEMODE_DEFAULT = "GameModeDefault";

    private DBManager dbManager;
    private ActivitySquadsBinding binding;
    private ArrayList<Squad> squads;
    private SquadsAdapter adapter;
    private GameMode gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySquadsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        dbManager = new DBManager(this);
        dbManager.open();

        setToolbar();

        ArrayAdapter<String> gameModeAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, GameMode.getListAllModeName());
        gameModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.squadsModeSpinner.setAdapter(gameModeAdapter);
        binding.squadsModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majGameMode();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Variable initialisation
        gameMode = (GameMode) getIntent().getSerializableExtra(EXTRA_GAMEMODE_DEFAULT);
        if(gameMode == null)
            gameMode = GameMode.getGameModeByName(binding.squadsModeSpinner.getSelectedItem().toString());

        //setSpinner on gameMode
        binding.squadsModeSpinner.setSelection(GameMode.getListAllModeName().indexOf(gameMode.getName()));

        binding.squadsModeCriterionTextView.setText(gameMode.getSquadRestrictionCriterion());
        squads = dbManager.getListSquadWithGameMode(gameMode);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.squadsMainRecycler.setLayoutManager(layoutManager);

        adapter = new SquadsAdapter(this, dbManager, squads, SquadsAdapter.SquadsAdapterMode.SQUADS);
        binding.squadsMainRecycler.setAdapter(this.adapter);

        binding.squadsImageButtonAddSquad.setOnClickListener(view->{
            //The generic squad name is Squad
            String squadName = AdditionalFunction.getNextValidNumberedName(dbManager.getListSquadWithGameModeNames(gameMode), Squad.GENERIC_SQUAD_NAME);
            Squad squad = new Squad(squadName, gameMode);
            dbManager.addSquad(squad);

            Intent intent = new Intent(this, EditSquadActivity.class);
            intent.putExtra("IDSquad", squad.getIDSquad());
            startActivity(intent);
        });

    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText("Squads editor");

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v-> back_function());
    }

    private void back_function(){
        this.startActivity(new Intent(this, MainMenu.class));
    }

    private void majGameMode(){
        gameMode = GameMode.getGameModeByName(binding.squadsModeSpinner.getSelectedItem().toString());

        binding.squadsModeCriterionTextView.setText(gameMode.getSquadRestrictionCriterion());

        squads = dbManager.getListSquadWithGameMode(gameMode);
        adapter = new SquadsAdapter(this, dbManager, squads,SquadsAdapter.SquadsAdapterMode.SQUADS);
        binding.squadsMainRecycler.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}