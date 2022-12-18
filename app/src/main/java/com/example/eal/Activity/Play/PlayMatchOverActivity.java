package com.example.eal.Activity.Play;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eal.Activity.MainMenu;
import com.example.eal.Adapter.MapWonnedAdapter;
import com.example.eal.Adapter.PlayersAdapter;
import com.example.eal.AdditionnalRessource.AdditionalFunction;
import com.example.eal.Application.App;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayMatchOverBinding;

import java.util.HashMap;

public class PlayMatchOverActivity extends AppCompatActivity {
    private ActivityPlayMatchOverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayMatchOverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        HashMap<Player, Float> playerByWin = Map.getPlayerByWin(PlayInGameActivity.getCurrentGamePlayers(), PlayInGameActivity.getCurrentGameMaps());
        Float mostWin = AdditionalFunction.getHighestValue(playerByWin);

        HashMap<Player, Float> winners = AdditionalFunction.keepHighValue(playerByWin, mostWin);

        StringBuilder displayWinner = new StringBuilder();
        int winnerCounter = 0;
        int winnerNumber = winners.size();
        for(Player winner: winners.keySet()){
            winnerCounter++;
            if(winnerCounter == winnerNumber)
                displayWinner.append(winner.getName());
            else if(winnerCounter == winnerNumber -1)
                displayWinner.append(String.format("%s and "));
            else
                displayWinner.append(String.format("%s, "));
        }
        displayWinner.append(" WON");

        binding.activityPlayMatchOverPlayerWon.setText(displayWinner);

        binding.activityPlayMatchOverPlayerRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.activityPlayMatchOverPlayerRecycler.setAdapter(new PlayersAdapter(PlayInGameActivity.getCurrentGamePlayers(), PlayInGameActivity.getCurrentGameMaps()));

        binding.activityPlayMatchOverExpandButton.setOnClickListener(v->{
            if(binding.activityPlayMatchOverPlayerRecycler.getVisibility() == View.VISIBLE){
                binding.activityPlayMatchOverPlayerRecycler.setVisibility(View.GONE);
                binding.activityPlayMatchOverExpandButton.setText("+");
            } else{
                binding.activityPlayMatchOverPlayerRecycler.setVisibility(View.VISIBLE);
                binding.activityPlayMatchOverExpandButton.setText("-");
            }
        });


        binding.activityPlayMatchOverMapWonnerRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.activityPlayMatchOverMapWonnerRecycler.setAdapter(new MapWonnedAdapter(this, PlayInGameActivity.getCurrentGameMaps()));

        binding.activityPlayMatchOverQuitButton.setOnClickListener(view->startActivity(new Intent(this, MainMenu.class)));
    }
}