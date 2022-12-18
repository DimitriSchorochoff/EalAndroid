package com.example.eal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eal.Class.Spell.Spell;
import com.example.eal.R;

import java.util.ArrayList;

public class ElementsAdapter extends RecyclerView.Adapter<ElementsAdapter.ViewHolder> {
    public static int numColumn = 6;
    private ArrayList<String> elementList;

    public ElementsAdapter(ArrayList<String> elementList){
        this.elementList = elementList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_element;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView_element = itemView.findViewById(R.id.cell_element_textView_element);
        }

        public void setElement(String element){
            this.textView_element.setBackgroundColor(Spell.getElementColor(element));
        }
    }

    @NonNull
    @Override
    public ElementsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_element, parent, false);
        return new ElementsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementsAdapter.ViewHolder holder, int position) {
        holder.setElement(elementList.get(position));

    }

    @Override
    public int getItemCount() {
        return elementList.size();
    }
}
