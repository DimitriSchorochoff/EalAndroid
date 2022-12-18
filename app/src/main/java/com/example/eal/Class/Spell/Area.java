package com.example.eal.Class.Spell;

import android.graphics.drawable.Drawable;

import com.example.eal.Application.App;
import com.example.eal.R;

public class Area {
    public int minimum;
    public int maximum;
    public AreaType type;

    public Area(int minimum, int maximum, AreaType type){
        this.minimum = minimum;
        this.maximum = maximum;
        this.type = type;
    }

    public String getName(){
        if(minimum == maximum)
            return String.format("%s %d", type.name(), maximum);
        else
            return String.format("%s %d-%d", type.name(), minimum, maximum);
    }

    private int getNumberTarget(AreaType type, int power){
        if(power == 0)
            return 1;

        switch (type){
            case CROSS:
            case DIAGONAL:
                return power*4 + 1;
            case STAR:
                return power*8 + 1;
            case SQUARE:
                return (int) (Math.pow(1+2*power, 2));
            case CLASSIC:
                return power*(power+1)*2 + 1; //Sum of natural till power * 4 (We sum the multiple perimeters)
            case ARC:
            case ARC_REVERSE:
                return 1 + 2*power;
            case ALL:
                return -1;

        }
        return -2;
    }

    public int getMaximumNumberTarget(){
        if(minimum > 0)
            return getNumberTarget(type, maximum) - getNumberTarget(type, minimum - 1);
        else
            return getNumberTarget(type, maximum);
    }

    public Drawable getAreaImage(){
        switch (getType()){
            case SQUARE:
                switch (getMaximum()){
                    case 1:
                        return App.getContext().getResources().getDrawable(R.drawable.ic_damage_icon);
                    case 2:
                        return App.getContext().getResources().getDrawable(R.drawable.ic_damage_icon);
                }
        }
        return null;
    }

    public AreaType getType() {
        return type;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public enum AreaType{
        CLASSIC,
        CROSS,
        DIAGONAL,
        SQUARE,
        STAR,
        ARC,
        ARC_REVERSE,
        ALL
    }
}
