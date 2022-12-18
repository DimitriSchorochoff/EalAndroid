package com.example.eal.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Activity.Play.PlayTurnTransitionActivity;
import com.example.eal.Activity.Squad.EditSquadActivity;
import com.example.eal.AdditionnalRessource.InterceptTouchConstraintLayout;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.GameMode.GameModeDeathMatch.ClassicGameMode;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Class.Squad;
import com.example.eal.Database.DBManager;
import com.example.eal.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SquadsAdapter extends RecyclerView.Adapter<SquadsAdapter.ViewHolder> {
    private AppCompatActivity context;
    private DBManager dbManager;
    private ArrayList<Squad> squads;
    private SquadsAdapterMode mode;
    private GameMode gameMode;
    private Player player;

    public SquadsAdapter(AppCompatActivity context, DBManager dbManager, ArrayList<Squad> squads, SquadsAdapterMode mode){
        this.context = context;
        this.dbManager = dbManager;
        this.squads = squads;
        this.mode = mode;
        if(squads.size()>0)
            this.gameMode = squads.get(0).getGameMode();
        else
            //Set a default value
            this.gameMode = new ClassicGameMode(0,0);
    }

    public SquadsAdapter(AppCompatActivity context, DBManager dbManager, ArrayList<Squad> squads, Player player){
        this.context = context;
        this.dbManager = dbManager;
        this.squads = squads;
        this.mode = SquadsAdapterMode.PLAYCHOOSESQUAD;
        this.player = player;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageButton imageButton_delete_squad;
        private TextView textView_name;
        private RecyclerView recycler;
        private InterceptTouchConstraintLayout interceptTouchConstraintLayout;
        private TextView num_members;

        public ViewHolder(View view)
        {
            super(view);
            imageButton_delete_squad = view.findViewById(R.id.cell_squad_imageButton_delete);
            textView_name = view.findViewById(R.id.cell_squad_textView_name);
            recycler = view.findViewById(R.id.cell_squad_recyler_element);
            interceptTouchConstraintLayout = view.findViewById(R.id.cell_squad_intercept_touch_recycler_layout);
            num_members = view.findViewById(R.id.cell_squad_textView_num_member);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_squad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Squad s = squads.get(position);

        holder.textView_name.setEnabled(false);
        holder.textView_name.setText(s.getName());

        ArrayList<String> listElem = new ArrayList<>();
        ArrayList<String> ealardElements;
        for (Ealard e: s.getMembers()){
            ealardElements = Spell.getBestSpellListElements(e.getSpellList());
            for (String element: ealardElements)
                if (! listElem.contains(element)) listElem.add(element);
        }

        Collections.sort(listElem, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, ElementsAdapter.numColumn);
        holder.recycler.setLayoutManager(layoutManager);

        ElementsAdapter elementsAdapter = new ElementsAdapter(listElem);
        holder.recycler.setAdapter(elementsAdapter);

        holder.interceptTouchConstraintLayout.setOnClickListener(v->
                holder.itemView.callOnClick());
        holder.interceptTouchConstraintLayout.setOnLongClickListener(v->
                holder.itemView.performLongClick());


        switch (mode){
            case SQUADS:
                if(!gameMode.isSquadValid(s)){
                    holder.textView_name.setBackgroundColor(context.getColor(R.color.invalid));
                } else {
                    holder.textView_name.setBackgroundColor(context.getColor(R.color.cell_background_color));
                }

                holder.imageButton_delete_squad.setOnClickListener(view->{
                    dbManager.deleteSquad(s.getIDSquad());
                    squads.remove(position);

                    //We need to hide the button otherwise after delete the below squad's button will be visible
                    holder.imageButton_delete_squad.setVisibility(View.GONE);
                    notifyDataSetChanged();
                });

                holder.itemView.setOnClickListener(view->{
                    Intent intent = new Intent(context.getApplicationContext(), EditSquadActivity.class);
                    intent.putExtra("IDSquad", squads.get(position).getIDSquad());
                    context.startActivity(intent);
                });

                holder.itemView.setOnLongClickListener(view->{
                    if(holder.imageButton_delete_squad.getVisibility() == View.GONE) {
                        holder.imageButton_delete_squad.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.imageButton_delete_squad.setVisibility(View.GONE);
                    }
                    return true;
                });

                holder.num_members.setVisibility(View.VISIBLE);
                holder.num_members.setText(Integer.toString(s.getMembers().size()) +
                        "/" +
                        Integer.toString(gameMode.getNumberEalardPerSquad()));

                break;

            case PLAYCHOOSESQUAD:
                holder.itemView.setOnClickListener(v->{
                    player.setOwnedSquadID(s.getIDSquad());

                    if(!PlayInGameActivity.launchNextChooseSquadActivity(context)){
                        if(!PlayInGameActivity.launchNextChooseEalardActivity(context)){
                            //Launch game
                            PlayInGameActivity.getCurrentGameTurnManager().startGame();
                            context.startActivity(new Intent(context, PlayTurnTransitionActivity.class));
                        }
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return squads.size();
    }

    public enum SquadsAdapterMode{
        SQUADS,
        PLAYCHOOSESQUAD
    }
}
