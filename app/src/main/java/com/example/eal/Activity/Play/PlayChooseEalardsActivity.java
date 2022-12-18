package com.example.eal.Activity.Play;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eal.Adapter.EalardsAdapter;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Player;
import com.example.eal.Class.Squad;
import com.example.eal.Database.DBManager;
import com.example.eal.Dialog.AreSureQuitGameDialog;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayChooseEalardsBinding;

import java.util.ArrayList;

public class PlayChooseEalardsActivity extends AppCompatActivity {
    public static final String EXTRA_PLAYER_POSITION = "PlayerPosition";

    private Bundle savedInstanceState;

    private ActivityPlayChooseEalardsBinding binding;
    private DBManager dbManager;
    private EalardsAdapter adapter;

    private Player playerToFill;
    private Squad squadToDisplay;
    private ArrayList<Ealard> selectedEalard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;

        super.onCreate(savedInstanceState);
        binding = ActivityPlayChooseEalardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);


        dbManager = new DBManager(this);
        dbManager.open();

        playerToFill = PlayInGameActivity.getCurrentGamePlayers()
                .get(getIntent().getIntExtra(EXTRA_PLAYER_POSITION,-1));
        squadToDisplay = dbManager.getSquad(playerToFill.getOwnedSquadID());

        //No need to choose ealards if all must be selected
        if(squadToDisplay.getMembers().size() == PlayInGameActivity.getLastMap().getNumberEalard()){
            selectedEalard = new ArrayList<>(squadToDisplay.getMembers());
            startNextActivity();
            return;
        }

        selectedEalard = new ArrayList<>();

        binding.playChooseEalardsGamemodeTextView.setText(PlayInGameActivity.getCurrentGameGameMode().getName());

        binding.playChooseEalardsRecylerEalards.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EalardsAdapter(this, dbManager, squadToDisplay.getMembers(), EalardsAdapter.EalardsAdapterMode.PLAYCHOOSEEALARDS);
        binding.playChooseEalardsRecylerEalards.setAdapter(adapter);

        //We check if it is the last activity before starting the game
        if(PlayInGameActivity.countNonPuppetPlayerWithoutInGameSquad() <= 1)
            binding.playChooseEalardsStartButton.setText("Start battle");

        binding.playChooseEalardsStartButton.setOnClickListener(v -> startNextActivity());

        setToolbar();
        majNumberEalardSelected();
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    private void setToolbar() {
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText(String.format("%s: ealards selection", playerToFill.getName()));

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v -> {
            if(!PlayInGameActivity.launchLastChooseEalardsActivity(this)){
                //If we played a least one game we don't pick squad anymore
                if(PlayInGameActivity.getCurrentGameMaps().size()>1){
                    PlayInGameActivity.removeLastMap();
                    startActivity(new Intent(this, PlayChooseMapActivity.class));
                } else{
                    PlayInGameActivity.launchLastChooseSquadActivity(this);
                }
            }
        });

        back_icon.setOnLongClickListener(v->{
            AreSureQuitGameDialog quitGameDialog = AreSureQuitGameDialog.newInstance();
            quitGameDialog.show(getSupportFragmentManager(), null);
            return true;
        });
    }

    public void startNextActivity(){
        int SquadSize = selectedEalard.size();
        int numberEalardPerSquad = PlayInGameActivity.getLastMap().getNumberEalard();
        if (SquadSize == numberEalardPerSquad) {
            playerToFill.setOwnedInGameSquad(new InGameSquad(selectedEalard, playerToFill, PlayInGameActivity.getCurrentGameTurnManager()));

            if(!PlayInGameActivity.launchNextChooseEalardActivity(this)){
                launchGame();
            }
        } else if (SquadSize < numberEalardPerSquad)
            Toast.makeText(this, "Please select more ealards", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Please select fewer ealards", Toast.LENGTH_SHORT).show();
    }

    public void majNumberEalardSelected() {
        binding.playChooseEalardsNumEalardsTextView.setText(
                String.format("%d / %d", selectedEalard.size(), PlayInGameActivity.getLastMap().getNumberEalard())
        );
    }

    private void launchGame(){
        PlayInGameActivity.getCurrentGameTurnManager().startGame();
        startActivity(new Intent(this, PlayTurnTransitionActivity.class));
    }

    public ArrayList<Ealard> getSelectedEalard() {
        return selectedEalard;
    }
}
