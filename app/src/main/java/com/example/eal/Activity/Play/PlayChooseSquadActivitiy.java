package com.example.eal.Activity.Play;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eal.Adapter.SquadsAdapter;
import com.example.eal.Application.App;
import com.example.eal.Class.Player;
import com.example.eal.Class.Squad;
import com.example.eal.Database.DBManager;
import com.example.eal.Dialog.AreSureDialog;
import com.example.eal.Dialog.AreSureQuitGameDialog;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayChooseSquadBinding;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayChooseSquadActivitiy extends AppCompatActivity {
    public static final String EXTRA_PLAYER_POSITION = "PlayerPosition";

    private ActivityPlayChooseSquadBinding binding;
    private DBManager dbManager;
    private SquadsAdapter adapter;

    private ArrayList<Squad> squads;
    private Player playerToFill;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayChooseSquadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        playerToFill = PlayInGameActivity
                .getCurrentGamePlayers().get(getIntent().getIntExtra(EXTRA_PLAYER_POSITION, -1));

        setToolbar();

        dbManager = new DBManager(this);
        dbManager.open();

        squads = filterValidSquads(dbManager.getListSquadWithGameMode(PlayInGameActivity.getCurrentGameGameMode()));

        binding.playChooseSquadGamemodeTextView.setText(PlayInGameActivity.getCurrentGameGameMode().getName());

        binding.playChooseSquadRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SquadsAdapter(this, dbManager, squads, playerToFill);
        binding.playChooseSquadRecycler.setAdapter(adapter);
    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText(String.format("%s: squad selection",playerToFill.getName()));

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v-> {
            if(!PlayInGameActivity.launchLastChooseSquadActivity(this)){
                PlayInGameActivity.removeLastMap();
                startActivity(new Intent(this, PlayChooseMapActivity.class));
            }
        });

        back_icon.setOnLongClickListener(v->{
            AreSureQuitGameDialog quitGameDialog = AreSureQuitGameDialog.newInstance();
            quitGameDialog.show(getSupportFragmentManager(), null);
            return true;
        });
    }

    public ArrayList<Squad> filterValidSquads(ArrayList<Squad> squads){
        Predicate<Squad> isValid = squad -> PlayInGameActivity.getCurrentGameGameMode().isSquadValid(squad);

        return squads.stream()
                .filter(isValid)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}
