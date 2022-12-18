package com.example.eal.Activity.Play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.eal.Application.App;
import com.example.eal.Class.Player;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayTransitionBinding;

public class PlayTurnTransitionActivity extends AppCompatActivity {
    private ActivityPlayTransitionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayTransitionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        Player activePlayer = PlayInGameActivity.getActivePlayer();
        binding.playTransitionPlayernameTV.setText(activePlayer.getName());

        binding.playTransitionStartButton.setText("Start turn");
        binding.playTransitionStartButton.setOnClickListener(v->startActivity(new Intent(this, PlayInGameActivity.class)));
    }
}