package com.example.eal.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Activity.Squad.EditEalardActivity;
import com.example.eal.Activity.Play.PlayChooseEalardsActivity;
import com.example.eal.Activity.Squad.EditSquadActivity;
import com.example.eal.AdditionnalRessource.InterceptTouchConstraintLayout;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Database.DBManager;
import com.example.eal.R;

import java.util.ArrayList;

public class EalardsAdapter extends RecyclerView.Adapter<EalardsAdapter.ViewHolder> {
    private AppCompatActivity context;
    private DBManager dbManager;
    private ArrayList<Ealard> ealards;
    private EalardsAdapterMode mode;

    public EalardsAdapter(AppCompatActivity context, DBManager dbManager, ArrayList<Ealard> ealards, EalardsAdapterMode mode){
        this.context = context;
        this.dbManager = dbManager;
        this.ealards = ealards;
        this.mode = mode;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout mainLayout;

        private ImageButton imageButton_delete_ealard;
        private TextView textView_name;
        private RecyclerView recycler;
        private InterceptTouchConstraintLayout interceptTouchConstraintLayout;

        private TextView textView_vitality;
        private TextView textView_energy;
        private TextView textView_mobility;

        public ViewHolder(View view)
        {
            super(view);
            this.mainLayout = view.findViewById(R.id.cell_entity_main_layout);

            this.imageButton_delete_ealard = view.findViewById(R.id.cell_entity_imageButton_delete);
            this.textView_name = view.findViewById(R.id.cell_entity_textView_name);
            this.recycler = view.findViewById(R.id.cell_entity_recycler_element);
            this.interceptTouchConstraintLayout = view.findViewById(R.id.cell_entity_intercept_touch_recycler_layout);

            this.textView_vitality = view.findViewById(R.id.cell_entity_textView_vitality);
            this.textView_energy = view.findViewById(R.id.cell_entity_textView_energy);
            this.textView_mobility = view.findViewById(R.id.cell_entity_textView_mobility);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_entity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ealard e = ealards.get(position);

        GameMode gameMode = dbManager.getSquadGameMode(e.getiDSquad());
        if (!gameMode.isEalardValid(e))
            holder.textView_name.setBackgroundColor(context.getResources().getColor(R.color.invalid));
        if(e.getEssence() > 0)
            holder.textView_name.setBackgroundColor(context.getResources().getColor(R.color.incomplete));


        holder.textView_name.setText(e.getName());
        holder.textView_name.setEnabled(false);

        holder.textView_vitality.setText(Integer.toString(e.getVitality()));
        holder.textView_energy.setText(Integer.toString(e.getEnergy()));
        holder.textView_mobility.setText(Integer.toString(e.getMobility()));


        ArrayList<String> listElem = Spell.getSpellListElements(e.getSpellList());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, ElementsAdapter.numColumn);
        holder.recycler.setLayoutManager(layoutManager);

        ElementsAdapter elementsAdapter = new ElementsAdapter(listElem);
        holder.recycler.setAdapter(elementsAdapter);

        holder.interceptTouchConstraintLayout.setOnClickListener(view->{
            holder.itemView.callOnClick();
        });

        holder.interceptTouchConstraintLayout.setOnLongClickListener(view->{
            holder.itemView.performLongClick();
            return true;
        });





        switch (mode){
            case SQUAD:
                holder.imageButton_delete_ealard.setVisibility(View.GONE);
                holder.imageButton_delete_ealard.setOnClickListener(view->{
                    EditSquadActivity editSquadActivity = (EditSquadActivity) context;
                    editSquadActivity.getBinding().activitySquadImageButtonAddEalard.setVisibility(View.VISIBLE);

                    dbManager.deleteEalard(e.getiDEalard());
                    ealards.remove(position);
                    notifyDataSetChanged();
                });


                holder.itemView.setOnClickListener(view-> {
                    Intent intent = new Intent(context, EditEalardActivity.class);
                    intent.putExtra("IDEalard", ealards.get(position).getiDEalard());
                    context.startActivity(intent);
                });
                holder.itemView.setOnLongClickListener(view->{
                    if(holder.imageButton_delete_ealard.getVisibility() == View.VISIBLE) {
                        holder.imageButton_delete_ealard.setVisibility(View.GONE);
                    }
                    else {
                        holder.imageButton_delete_ealard.setVisibility(View.VISIBLE);
                    }
                    return true;
                });

                break;

            case PLAYCHOOSEEALARDS:
                PlayChooseEalardsActivity playChooseEalardsActivity = (PlayChooseEalardsActivity) context;
                holder.itemView.setOnClickListener(view->{
                    if(playChooseEalardsActivity.getSelectedEalard().contains(e)){
                        holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.cell_background_color));
                        playChooseEalardsActivity.getSelectedEalard().remove(e);
                    } else{
                        holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.selected));
                        playChooseEalardsActivity.getSelectedEalard().add(e);
                    }
                    playChooseEalardsActivity.majNumberEalardSelected();
                });
                break;

        }
    }

    @Override
    public int getItemCount() {
        return ealards.size();
    }

    public enum EalardsAdapterMode{
        SQUAD,
        PLAYCHOOSEEALARDS
    }
}