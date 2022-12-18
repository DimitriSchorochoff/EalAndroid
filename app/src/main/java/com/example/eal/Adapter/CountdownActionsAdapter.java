package com.example.eal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Class.TurnManager.CountdownAction;
import com.example.eal.Application.App;
import com.example.eal.R;

import java.util.ArrayList;

public class CountdownActionsAdapter extends RecyclerView.Adapter<CountdownActionsAdapter.ViewHolder> {
    private ArrayList<CountdownAction> listToDisplay;

    public CountdownActionsAdapter(ArrayList<CountdownAction> listToDisplay){
        this.listToDisplay = listToDisplay;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView description;
        private TextView countdown;
        private ImageView expand_button;

        private boolean isExpanded;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.cell_countdown_action_description_tV);
            countdown = itemView.findViewById(R.id.cell_countdown_action_countdown_tV);
            expand_button = itemView.findViewById(R.id.cell_countdown_action_expand_button);

            isExpanded = true;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_countdown_action, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountdownAction countdownAction = listToDisplay.get(position);

        holder.countdown.setText(Integer.toString(countdownAction.getCountdown()));

        if(countdownAction.hasSameDescription()){
            holder.expand_button.setVisibility(View.INVISIBLE);
            holder.description.setText(countdownAction.getDescription());
        } else{
            holder.expand_button.setOnClickListener(view->{
                if(holder.isExpanded){
                    holder.description.setText(countdownAction.getSmallDescription());
                    holder.expand_button.setImageDrawable(App.getContext().getDrawable(R.drawable.ic_expand_more_icon));
                    holder.isExpanded = false;
                } else{
                    holder.description.setText(countdownAction.getDescription());
                    holder.expand_button.setImageDrawable(App.getContext().getDrawable(R.drawable.ic_expand_less_icon));
                    holder.isExpanded = true;
                }
            });
            //One click to start display
            holder.expand_button.callOnClick();
        }
    }

    @Override
    public int getItemCount() {
        return listToDisplay.size();
    }
}
