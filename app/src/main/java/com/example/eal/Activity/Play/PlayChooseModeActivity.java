package com.example.eal.Activity.Play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eal.Activity.MainMenu;
import com.example.eal.Application.App;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.Player;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayChooseModeBinding;

import java.util.ArrayList;

public class PlayChooseModeActivity extends AppCompatActivity {
    private ActivityPlayChooseModeBinding binding;

    private static final int roadToStartingNumber = 2;
    private static final int playerStartingNumber = 2;
    private static final int puppetStartingNumber = 0;

    private GameMode gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayChooseModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        ArrayAdapter<String> gameModeAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, GameMode.getListAllModeName());
        gameModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.activityPlayChooseModeSpinner.setAdapter(gameModeAdapter);
        binding.activityPlayChooseModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majGameMode();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setToolbar();

        //Whenever we enter this activity we reset all we need to avoid conflict
        PlayInGameActivity.reset();

        setNumberRoadTo(roadToStartingNumber);
        binding.activityPlayChooseModeRoadToPlus.setOnClickListener(view->{
            setNumberRoadTo(getNumberRoadTo()+1);
        });
        binding.activityPlayChooseModeRoadToMinus.setOnClickListener(view->{
            int numberRoadTo = getNumberRoadTo();
            if(numberRoadTo <= 1)
                Toast.makeText(this, "Can't go below 1 round to win", Toast.LENGTH_SHORT).show();
            else
                setNumberRoadTo(numberRoadTo-1);
        });

        setNumberPlayer(playerStartingNumber);
        binding.activityPlayChooseModeNumberPlayerPlus.setOnClickListener(view->{
            int numberParticipant = getNumberParticipant();
            if(numberParticipant >= gameMode.getNumberPlayerMax()){
                Toast.makeText(this, String.format("%s has maximum %d participants.",gameMode.getName(), gameMode.getNumberPlayerMax() ), Toast.LENGTH_SHORT).show();
            } else{
                setNumberPlayer(getNumberPlayer()+1);
            }
        });

        binding.activityPlayChooseModeNumberPlayerMinus.setOnClickListener(view->{
            int numberParticipant = getNumberParticipant();
            if(numberParticipant <= gameMode.getNumberPlayerMin()){
                Toast.makeText(this, String.format("%s need at least %d participants.",gameMode.getName(), gameMode.getNumberPlayerMin() ), Toast.LENGTH_SHORT).show();
            } else if(getNumberPlayer() <= 1){
                Toast.makeText(this, "You need at least 1 player to play...", Toast.LENGTH_SHORT).show();
            } else {
                setNumberPlayer(getNumberPlayer() - 1);
            }
        });


        setNumberPuppet(puppetStartingNumber);
        binding.activityPlayChooseModeNumberPuppetPlus.setOnClickListener(view->{
            int numberParticipant = getNumberParticipant();
            if(numberParticipant >= gameMode.getNumberPlayerMax()){
                Toast.makeText(this, String.format("%s has maximum %d participants.",gameMode.getName(), gameMode.getNumberPlayerMax() ), Toast.LENGTH_SHORT).show();
            } else{
                setNumberPuppet(getNumberPuppet()+1);
            }
        });

        binding.activityPlayChooseModeNumberPuppetMinus.setOnClickListener(view->{
            int numberParticipant = getNumberParticipant();
            if(numberParticipant <= gameMode.getNumberPlayerMin()){
                Toast.makeText(this, String.format("%s need at least %d participants.",gameMode.getName(), gameMode.getNumberPlayerMin() ), Toast.LENGTH_SHORT).show();
            } else if(getNumberPuppet() <= 0){
                Toast.makeText(this, "Can't go below 0 puppet", Toast.LENGTH_SHORT).show();
            } else {
                setNumberPuppet(getNumberPuppet() - 1);
            }
        });

        binding.activityPlayChooseModeNextButton.setOnClickListener(v->{
            gameMode.setNumberPlayer(getNumberParticipant());
            gameMode.setNumberRoadTo(getNumberRoadTo());
            PlayInGameActivity.setCurrentGameGameMode(gameMode);

            initPlayer();

            startActivity(new Intent(this, PlayChooseMapActivity.class));
        });

    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText("Mode selection");

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v-> startActivity(new Intent(this, MainMenu.class)));
    }

    private void initPlayer(){
        ArrayList<Player> players = PlayInGameActivity.getCurrentGamePlayers();
        //Prevent to initialise multiple time players
        if(players.size() == 0){
            for(int i = 1; i<=getNumberPlayer(); i++){
                players.add(new Player(String.format(Player.GENERIC_PLAYER_NAME + "_%d", i),false, PlayInGameActivity.getCurrentGameGameMode()));
            }
            for(int i = 1; i<=getNumberPuppet(); i++){
                players.add(new Player(String.format(Player.GENERIC_PUPPET_NAME + "_%d", i),true, PlayInGameActivity.getCurrentGameGameMode()));
            }
        }
    }
    private void majGameMode(){
        gameMode = GameMode.getGameModeByName(binding.activityPlayChooseModeSpinner.getSelectedItem().toString());
    }

    private void setNumberRoadTo(int num){
        binding.activityPlayChooseModeRoadToNum.setText(Integer.toString(num));
    }

    private int getNumberRoadTo(){
        return Integer.parseInt(binding.activityPlayChooseModeRoadToNum.getText().toString());
    }

    private void setNumberPlayer(int num){
        binding.activityPlayChooseModeNumberPlayerNum.setText(Integer.toString(num));
    }

    private void setNumberPuppet(int num){
        binding.activityPlayChooseModeNumberPuppetTextView.setText(Integer.toString(num));
    }

    private int getNumberParticipant(){
        return Integer.parseInt(binding.activityPlayChooseModeNumberPlayerNum.getText().toString()) +
                Integer.parseInt(binding.activityPlayChooseModeNumberPuppetTextView.getText().toString());
    }

    private int getNumberPlayer(){
        return Integer.parseInt(binding.activityPlayChooseModeNumberPlayerNum.getText().toString());
    }

    private int getNumberPuppet(){
        return Integer.parseInt(binding.activityPlayChooseModeNumberPuppetTextView.getText().toString());
    }

}