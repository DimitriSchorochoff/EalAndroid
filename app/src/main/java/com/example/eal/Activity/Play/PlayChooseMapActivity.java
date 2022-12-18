package com.example.eal.Activity.Play;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eal.Adapter.MapsAdapter;
import com.example.eal.Adapter.PlayersAdapter;
import com.example.eal.Application.App;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.Dialog.MessageDialog;
import com.example.eal.Dialog.AreSureQuitGameDialog;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayChooseMapBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PlayChooseMapActivity extends AppCompatActivity implements MessageDialog.Listener{
    private ActivityPlayChooseMapBinding binding;

    private PlayersAdapter playersAdapter;
    private MapsAdapter mapsAdapter;

    private ArrayList<Map> availableMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayChooseMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        //We need to reset InGameSquad every time we enter the map selection/every start of game
        PlayInGameActivity.resetInGameSquad();

        //Check if the match is over
        if(Map.getMostWin(PlayInGameActivity.getCurrentGamePlayers(), PlayInGameActivity.getCurrentGameMaps())
                >= PlayInGameActivity.getCurrentGameGameMode().getNumberRoadTo()){
            startActivity(new Intent(this, PlayMatchOverActivity.class));
            return;
        }

        setToolbar();

        binding.activityPlayChooseMapGamemodeTextView.setText(PlayInGameActivity.getCurrentGameGameMode().getName());

        binding.activityPlayChooseMapRoadToValue.setText(
                Integer.toString(PlayInGameActivity.getCurrentGameGameMode().getNumberRoadTo()));

        binding.activityPlayChooseMapPlayerRecycler.setLayoutManager(new LinearLayoutManager(this));
        playersAdapter = new PlayersAdapter(PlayInGameActivity.getCurrentGamePlayers(), PlayInGameActivity.getCurrentGameMaps());
        binding.activityPlayChooseMapPlayerRecycler.setAdapter(playersAdapter);

        binding.activityPlayChooseMapRoadToMinimizeButton.setOnClickListener(v->{
            if(binding.activityPlayChooseMapPlayerRecycler.getVisibility() == View.VISIBLE){
                binding.activityPlayChooseMapPlayerRecycler.setVisibility(View.GONE);
                binding.activityPlayChooseMapRoadToMinimizeButton.setText("+");
            } else{
                binding.activityPlayChooseMapPlayerRecycler.setVisibility(View.VISIBLE);
                binding.activityPlayChooseMapRoadToMinimizeButton.setText("-");
            }
        });

        Player lastPlayerWithLeastWin = Map.getLastPlayerWithLeastWin(PlayInGameActivity.getCurrentGamePlayers(), PlayInGameActivity.getCurrentGameMaps());
        if(PlayInGameActivity.getCurrentGameMaps().size() > 0){
            binding.activityPlayChooseMapChooserIndicationTextView.setText(
                    String.format("It's %s turn to choose the map",lastPlayerWithLeastWin.getName()));
        } else {
            ItemTouchHelper.SimpleCallback playerReorderingCallBack = new ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                    0) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    int fromPos = viewHolder.getAdapterPosition();
                    int toPos = target.getAdapterPosition();

                    Collections.swap(PlayInGameActivity.getCurrentGamePlayers(),fromPos, toPos);
                    recyclerView.getAdapter().notifyItemMoved(fromPos,toPos);
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                }
            };

            ItemTouchHelper playerReorderingHelper = new ItemTouchHelper(playerReorderingCallBack);
            playerReorderingHelper.attachToRecyclerView(binding.activityPlayChooseMapPlayerRecycler);


            binding.activityPlayChooseMapChooserIndicationTextView.setText(
                    "It's last player turn to choose the map");
        }


        availableMaps = Map.getListMapsByGameMode(PlayInGameActivity.getCurrentGameGameMode());
        filterAvailableMapsByPlayedMaps();

        binding.activityPlayChooseMapMapRecycler.setLayoutManager(new LinearLayoutManager(this));
        mapsAdapter = new MapsAdapter(this, availableMaps);
        binding.activityPlayChooseMapMapRecycler.setAdapter(mapsAdapter);

        if(PlayInGameActivity.getLastMap() != null){
            MessageDialog dialog = MessageDialog.newInstance(this);
            dialog.show(getSupportFragmentManager(), null);
        }

    }

    private void setToolbar() {
        TextView title = findViewById(R.id.tool_bar_title);
        title.setText("Score and map selection");

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v -> {
            if (PlayInGameActivity.getCurrentGameMaps().size() > 0) {
                AreSureQuitGameDialog quitGameDialog = AreSureQuitGameDialog.newInstance();
                quitGameDialog.show(getSupportFragmentManager(), null);
            } else {
                startActivity(new Intent(this, PlayChooseModeActivity.class));
            }
        });
    }

    private void filterAvailableMapsByPlayedMaps(){
        HashMap<String, Integer> mapNameByNumberPlay= new HashMap<>();
        for(Map m: availableMaps){
            mapNameByNumberPlay.put(m.getName(), 0);
        }

        int numberTimePlayed;
        for(Map m: PlayInGameActivity.getCurrentGameMaps()){
            //The default part is in case you played map that are no more available
            numberTimePlayed = mapNameByNumberPlay.getOrDefault(m.getName(), 0);

            mapNameByNumberPlay.put(m.getName(), numberTimePlayed+1);
        }

        int minimumTimePlayed = Integer.MAX_VALUE;
        for(int timePlayed: mapNameByNumberPlay.values()){
            minimumTimePlayed = Integer.min(minimumTimePlayed, timePlayed);
        }

        //We keep only maps that were the least played
        ArrayList<Map> mapsToRemove = new ArrayList<>();
        for(Map m: availableMaps){
            if(mapNameByNumberPlay.get(m.getName()) > minimumTimePlayed)
                mapsToRemove.add(m);
        }
        availableMaps.removeAll(mapsToRemove);
    }

    //MessageDialog.Listener
    @Override
    public String getMessage() {return "Game ended";}

    @Override
    public String getAdditionalMessage() {
        Map map = PlayInGameActivity.getLastMap();
        if(map.getWinner() == null)
            return "It's a draw";
        else
            return String.format("%s won", map.getWinner().getName());
    }

    @Override
    public void onQuitMessageDialog() {}
}