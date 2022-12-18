package com.example.eal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eal.Application.App;
import com.example.eal.R;

public class RuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        App.setActivity(this);

        setToolbar();
    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText("Rule");

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v-> back_function());
    }

    private void back_function(){
        this.startActivity(new Intent(this, MainMenu.class));
    }
}