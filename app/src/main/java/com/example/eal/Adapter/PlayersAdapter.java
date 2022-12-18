package com.example.eal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Application.App;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.AdditionnalRessource.AdditionalFunction;
import com.example.eal.R;

import java.util.ArrayList;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private ArrayList<Player> players;
    private ArrayList<String> playersName;
    private ArrayList<Map> maps;

    public PlayersAdapter(ArrayList<Player> players, ArrayList<Map> maps) {
        this.players = players;
        this.playersName = new ArrayList<>();
        for(Player player: players){
            this.playersName.add(player.getName());
        }
        this.maps = maps;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText name;
        private TextView score;
        private ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.cell_player_name);
            this.score = itemView.findViewById(R.id.cell_player_score_textView);
            this.icon = itemView.findViewById(R.id.cell_player_icon);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player p = players.get(position);

        holder.name.setText(p.getName());
        if(p.getOwnedSquadID() == null){
            //First time in PlayChooseMap
            holder.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        String currentName = holder.name.getText().toString();
                        //Prevent user from choosing invisible name
                        if(AdditionalFunction.containsOnlySpace(currentName)){
                            if(p.isPuppetPlayer())
                                currentName = Player.GENERIC_PUPPET_NAME;
                            else
                                currentName = Player.GENERIC_PLAYER_NAME;
                        }
                        //Clear currentName from playersName while were checking the name with getNextValidNumberedName()
                        playersName.set(position, "");

                        String newName = AdditionalFunction.getNextValidNumberedName(playersName, currentName);
                        holder.name.setText(newName);
                        p.setName(newName);
                        playersName.set(position, newName);
                    }
                }
            });
        } else{
            holder.name.setEnabled(false);
        }

        float playerScore = PlayInGameActivity.getPlayerScore(p);
        if(playerScore%1 == 0.0)
            //Prevent show decimal part if not needed
            holder.score.setText(Integer.toString((int) playerScore));
        else
            holder.score.setText(Float.toString(playerScore));

        if(p.isPuppetPlayer())
            holder.icon.setImageDrawable(App.getContext().getDrawable(R.drawable.ic_puppet_icon));
        else
            holder.icon.setImageDrawable(App.getContext().getDrawable(R.drawable.ic_player_icon));

    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
