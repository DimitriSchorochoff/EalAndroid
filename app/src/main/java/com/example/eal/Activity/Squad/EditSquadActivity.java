package com.example.eal.Activity.Squad;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.AlertDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.eal.Adapter.EalardsAdapter;
        import com.example.eal.Application.App;
        import com.example.eal.Class.Entities.Ealard;
        import com.example.eal.Class.Squad;
        import com.example.eal.Database.DBManager;
        import com.example.eal.AdditionnalRessource.AdditionalFunction;
        import com.example.eal.R;
        import com.example.eal.databinding.ActivitySquadBinding;
        import com.example.eal.databinding.DialogAreSureBinding;

        import java.util.ArrayList;

public class EditSquadActivity extends AppCompatActivity {
    private DBManager dbManager;
    private ActivitySquadBinding binding;
    private EalardsAdapter adapter;

    private Squad s;
    private ArrayList<Ealard> ealards;

    private ArrayList<String> usedSquadName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySquadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        dbManager = new DBManager(this);
        dbManager.open();

        String IDSquad = getIntent().getStringExtra("IDSquad");
        s = dbManager.getSquad(IDSquad);
        ealards = s.getMembers();

        setToolbar();

        usedSquadName = dbManager.getListSquadWithGameModeNames(s.getGameMode());
        binding.activitySquadNameEditText.setText(s.getName());
        binding.activitySquadNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newName = binding.activitySquadNameEditText.getText().toString();
                if(!newName.equals(EditSquadActivity.this.s.getName()) && usedSquadName.contains(newName))
                    binding.activitySquadNameEditText.setBackgroundColor(App.getContext().getResources().getColor(R.color.invalid));
                else
                    binding.activitySquadNameEditText.setBackgroundColor(App.getContext().getColor(R.color.white));
            }
        });

        binding.activitySquadModeTextView.setText(s.getGameMode().getName());
        binding.activitySquadModeCriterionTextView.setText(s.getGameMode().getEalardRestrictionCriterion());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.activitySquadMainRecycler.setLayoutManager(layoutManager);

        this.adapter = new EalardsAdapter(this, dbManager, ealards, EalardsAdapter.EalardsAdapterMode.SQUAD);
        binding.activitySquadMainRecycler.setAdapter(this.adapter);


        if(s.getMembers().size() >= s.getGameMode().getNumberEalardPerSquad())
            binding.activitySquadImageButtonAddEalard.setVisibility(View.GONE);

        binding.activitySquadImageButtonAddEalard.setOnClickListener(view->{
            onQuit();

            Intent intent = new Intent(this, EditEalardActivity.class);
            intent.putExtra("IDSquad", IDSquad);
            this.startActivity(intent);
        });

    }

    private void setToolbar(){
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText("Squad editor");

        ImageView return_icon = findViewById(R.id.tool_bar_return_icon);
        return_icon.setVisibility(View.VISIBLE);
        return_icon.setOnClickListener(v->back_function());

        ImageView save_icon = findViewById(R.id.tool_bar_save_icon);
        save_icon.setVisibility(View.VISIBLE);
        save_icon.setOnClickListener(v-> {
            Squad.setBufferIDSquadByGameMode(s.getGameMode() , s.getIDSquad());
            Toast.makeText(this, "Squad saved in the buffer", Toast.LENGTH_SHORT).show();
        });

        ImageView import_icon = findViewById(R.id.tool_bar_import_icon);
        import_icon.setVisibility(View.VISIBLE);
        import_icon.setOnClickListener(v->{
            String iDSquadToCopy = Squad.getBufferIDSquadByGameMode(s.getGameMode());
            if(iDSquadToCopy != null){
                if (iDSquadToCopy.equals(s.getIDSquad())) {
                    Toast.makeText(this, "To import this squad, select another one before", Toast.LENGTH_SHORT).show();
                } else{
                    if (s.getMembers().size() == 0)
                        import_Squad_from_buffer();
                    else
                        sure_copy_dialog();
                }
            } else
                Toast.makeText(this, "The buffer is empty", Toast.LENGTH_SHORT).show();
        });
    }

    private void back_function(){
        onQuit();

        Intent intent = new Intent(this, EditSquadsActivity.class);
        intent.putExtra(EditSquadsActivity.EXTRA_GAMEMODE_DEFAULT, s.getGameMode());
        this.startActivity(intent);
    }

    private void sure_copy_dialog(){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        DialogAreSureBinding binding = DialogAreSureBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(binding.getRoot());

        AlertDialog dialog = dialogBuilder.create();

        binding.dialogAreSureTextViewMessage.setText("Your current ealards will be erased");

        binding.dialogAreSureButtonYes.setOnClickListener(view->{
            import_Squad_from_buffer();

            dialog.dismiss();
        });

        binding.dialogAreSureButtonNo.setOnClickListener(view->{
            dialog.dismiss();
        });

        binding.dialogAreSureImageViewQuit.setOnClickListener(view->{
            dialog.dismiss();
        });

        dialog.show();
    }

    private void import_Squad_from_buffer(){
        Squad squadToCopy = dbManager.getSquad(Squad.getBufferIDSquadByGameMode(s.getGameMode()));

        for(Ealard e: s.getMembers()) {
            dbManager.deleteEalard(e.getiDEalard());
        }
        //Put outside of loop cause of concurrent modif exception
        ealards.removeAll(ealards);

        for (Ealard ealardToCopy: squadToCopy.getMembers()) {
            //Overcharged constructor that copy an Ealard
            Ealard newEalard = Ealard.copyEalard(dbManager, ealardToCopy.getiDEalard(), s.getIDSquad());
            dbManager.addEalard(newEalard);
            ealards.add(newEalard);
        }
        adapter.notifyDataSetChanged();

        ArrayList<String> usedSquadNameWithoutCurrentOne = new ArrayList<>(usedSquadName);
        usedSquadNameWithoutCurrentOne.remove(s.getName());

        String squadNameCopied = AdditionalFunction.getNextValidNumberedName(usedSquadNameWithoutCurrentOne, squadToCopy.getName());
        binding.activitySquadNameEditText.setText(squadNameCopied);
        s.setName(squadNameCopied);
        dbManager.editSquad(s);

        Toast.makeText(this, "Squad successfully copied", Toast.LENGTH_SHORT).show();
    }

    public ActivitySquadBinding getBinding() {
        return binding;
    }

    private void onQuit(){
        String newSquadName = binding.activitySquadNameEditText.getText().toString();
        if(!newSquadName.equals(s.getName()))
            newSquadName = AdditionalFunction.getNextValidNumberedName(usedSquadName, newSquadName);
        s.setName(newSquadName);
        dbManager.editSquad(s);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}