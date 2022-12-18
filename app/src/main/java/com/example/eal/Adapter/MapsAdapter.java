package com.example.eal.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Activity.Play.PlayTurnTransitionActivity;
import com.example.eal.Class.Map.Map;
import com.example.eal.R;

import java.util.ArrayList;

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.ViewHolder> {
    private AppCompatActivity activity;
    private ArrayList<Map> maps;

    public MapsAdapter(AppCompatActivity activity, ArrayList<Map> maps){
        this.activity = activity;
        this.maps = maps;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView numberEalard;
        private TextView numberPlayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cell_map_name);
            numberEalard = itemView.findViewById(R.id.cell_map_number_ealard_textView);
            numberPlayer = itemView.findViewById(R.id.cell_map_number_player_textView);
        }
    }

    @NonNull
    @Override
    public MapsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_map, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapsAdapter.ViewHolder holder, int position) {
        Map map = maps.get(position);

        holder.name.setText(map.getName());
        holder.numberEalard.setText(Integer.toString(map.getNumberEalard()));
        if(map.getMinNumberPlayer() == map.getMaxNumberPlayer()){
            holder.numberPlayer.setText(Integer.toString(map.getMinNumberPlayer()));
        } else{
            holder.numberPlayer.setText(String.format("%d-%d", map.getMinNumberPlayer(), map.getMaxNumberPlayer()));
        }

        holder.itemView.setOnClickListener(v->{
            //We clear focus in case onChangeFocus() need to be called before leaving
            View focusedView = activity.getCurrentFocus();
            if (focusedView != null) focusedView.clearFocus();

            PlayInGameActivity.getCurrentGameMaps().add(map);

            PlayInGameActivity.setPuppetInGameSquad(map, PlayInGameActivity.getCurrentGameGameMode(), PlayInGameActivity.getCurrentGameTurnManager());

            if(!PlayInGameActivity.launchNextChooseSquadActivity(activity)){
                if(!PlayInGameActivity.launchNextChooseEalardActivity(activity)){
                    //Launch game
                    PlayInGameActivity.getCurrentGameTurnManager().startGame();
                    activity.startActivity(new Intent(activity, PlayTurnTransitionActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }
}
