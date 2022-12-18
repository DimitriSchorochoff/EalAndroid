package com.example.eal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.Dialog.HistoricDialog;
import com.example.eal.R;

import java.util.ArrayList;

public class MapWonnedAdapter extends RecyclerView.Adapter<MapWonnedAdapter.ViewHolder> implements HistoricDialog.Listener{
    private AppCompatActivity activity;
    private ArrayList<Map> maps;

    private int mapClickedPosition;
    private Map mapClicked;

    public MapWonnedAdapter(AppCompatActivity activity, ArrayList<Map> maps){
        this.activity = activity;
        this.maps = maps;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mapName;
        private TextView playerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mapName = itemView.findViewById(R.id.cell_map_wonned_map_name);
            playerName = itemView.findViewById(R.id.cell_map_wonned_player_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_map_wonned, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map map = maps.get(position);

        holder.mapName.setText(map.getName());

        Player mapWinner = map.getWinner();
        if(mapWinner == null)
            holder.playerName.setText("Tie");
        else
            holder.playerName.setText(mapWinner.getName());

        holder.itemView.setOnClickListener(v->{
            mapClickedPosition = position;
            mapClicked = map;
            HistoricDialog dialog = HistoricDialog.newInstance(this);
            dialog.show(activity.getSupportFragmentManager(), null);
        });
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }

    //Listener implementation
    @Override
    public String getTitle() {
        return String.format("Game %d: %s", mapClickedPosition+1, mapClicked.getName());
    }

    @Override
    public String getHistoric() {
        return mapClicked.getMatchHistoric();
    }

    @Override
    public void onQuitHistoricDialog() {}
}
