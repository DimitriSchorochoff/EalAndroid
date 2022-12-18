package com.example.eal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.eal.Activity.Play.PlayChooseModeActivity;
import com.example.eal.Activity.Squad.EditSquadsActivity;
import com.example.eal.Application.App;
import com.example.eal.databinding.ActivityMainMenuBinding;

public class MainMenu extends AppCompatActivity {
    private ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        binding.buttonPlay.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PlayChooseModeActivity.class)));
        binding.buttonSquads.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EditSquadsActivity.class)));
        binding.buttonLibrary.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LibraryActivity.class)));
        binding.buttonRule.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RuleActivity.class)));
    }

}