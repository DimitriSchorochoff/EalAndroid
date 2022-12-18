package com.example.eal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eal.Adapter.ElementsAdapter;
import com.example.eal.Application.App;
import com.example.eal.Database.DBManager;
import com.example.eal.R;
import com.example.eal.databinding.ActivityLibraryBinding;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {
    private ActivityLibraryBinding binding;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        dbManager = new DBManager(this);
        dbManager.open();

        binding.libraryRecycler.setHasFixedSize(true);

        ArrayList<String> elements = new ArrayList<>();
        elements.add("air");
        elements.add("fire");

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, elements.size());
        binding.libraryRecycler.setLayoutManager(layoutManager);

        ElementsAdapter elementsAdapter = new ElementsAdapter(elements);
        binding.libraryRecycler.setAdapter(elementsAdapter);

        setToolbar();
    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText("Library");

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v-> back_function());

        findViewById(R.id.tool_bar);
    }

    private void back_function(){
        this.startActivity(new Intent(this, MainMenu.class));
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}