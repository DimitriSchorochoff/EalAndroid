package com.example.eal.Activity.Play;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Application.App;
import com.example.eal.Class.Player;
import com.example.eal.databinding.ActivityPlayTransitionBinding;

public class PlayChooseEalardsTransitionActivity extends AppCompatActivity {
    public static final String EXTRA_PLAYER_POSITION = "PlayerPosition";

    private ActivityPlayTransitionBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayTransitionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        int positionPlayerToFill = getIntent().getIntExtra(EXTRA_PLAYER_POSITION, -1);
        Player nextPlayerToChoose = PlayInGameActivity.getCurrentGamePlayers().get(positionPlayerToFill);

        binding.playTransitionPlayernameTV.setText(nextPlayerToChoose.getName());

        binding.playTransitionStartButton.setText("Choose ealards");
        binding.playTransitionStartButton.setOnClickListener(view->{
            Intent intent = new Intent(this, PlayChooseEalardsActivity.class);
            intent.putExtra(PlayChooseEalardsActivity.EXTRA_PLAYER_POSITION, positionPlayerToFill);
            this.startActivity(intent);
        });


    }
}
