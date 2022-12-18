package com.example.eal.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.AdditionnalRessource.AdditionalFunction;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Dialog.TargetDialog;
import com.example.eal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class PlayerAndTargetsAdapter extends RecyclerView.Adapter<PlayerAndTargetsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Player> players;
    private InGameEntity caster;

    private HashMap<InGameEntity, Integer> spellTargets;
    private TargetDialog dialog;
    private Predicate<InGameEntity> targetFilter;

    public  PlayerAndTargetsAdapter(Context context, ArrayList<Player> players, InGameEntity caster, HashMap<InGameEntity, Integer> spellTargets, TargetDialog dialog, Predicate<InGameEntity> targetFilter){
        this.context = context;
        this.players = players;
        this.caster = caster;

        this.spellTargets = spellTargets;
        this.dialog = dialog;
        this.targetFilter = targetFilter;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        RecyclerView recycler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.cell_player_target_name);
            this.recycler = itemView.findViewById(R.id.cell_player_target_recycler);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_player_and_targets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player player = players.get(position);

        if(player.getOwnedInGameSquad().containsInGameEntity(caster)){
            SpannableString content = new SpannableString(player.getName());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            holder.name.setText(content);
        } else {
            holder.name.setText(player.getName());
        }

        holder.recycler.setLayoutManager(new LinearLayoutManager(context));
        InGameEntitiesAdapter adapter = new InGameEntitiesAdapter(context, player, position, spellTargets, dialog, targetFilter);
        holder.recycler.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
