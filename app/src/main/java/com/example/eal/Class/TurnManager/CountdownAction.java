package com.example.eal.Class.TurnManager;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Class.Map.Map;

public class CountdownAction {
    protected Runnable action;
    protected int countdown;
    protected String smallDescription;
    protected String description;
    protected TurnManager turnManager;

    protected boolean hasSameDescription;

    public CountdownAction(Runnable action, int countdown, String description, TurnManager turnManager){
        this.action = action;
        this.countdown = countdown;
        this.smallDescription = description;
        this.description = description;
        this.hasSameDescription = true;
        this.turnManager = turnManager;
    }

    public CountdownAction(Runnable action, int countdown, String smallDescription, String description, TurnManager turnManager){
        this.action = action;
        this.countdown = countdown;
        this.smallDescription = smallDescription;
        this.description = description;
        this.hasSameDescription = false;
        this.turnManager = turnManager;
    }

    public String getDescription() {
        return description;
    }

    public int getCountdown() {
        return countdown;
    }

    public Runnable getAction() {
        return action;
    }

    public String getSmallDescription() {
        return smallDescription;
    }

    public void decCountDown() {
        this.countdown--;
    }

    public boolean hasSameDescription() {
        return hasSameDescription;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public void execute(){
        PlayInGameActivity.getLastMap().writeLineInHistoric(smallDescription, Map.SPELL_AND_ACTION_INDENTATION);
        getAction().run();
    }
}
