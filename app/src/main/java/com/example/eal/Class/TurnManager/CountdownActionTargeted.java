package com.example.eal.Class.TurnManager;

import com.example.eal.Class.InGame.InGameEntity;

public class CountdownActionTargeted extends CountdownAction{
    protected InGameEntity target;


    public CountdownActionTargeted(Runnable action, int countdown, String description, TurnManager turnManager, InGameEntity target) {
        super(action, countdown, description, turnManager);
        this.target = target;
    }

    public CountdownActionTargeted(Runnable action, int countdown, String smallDescription, String description, TurnManager turnManager, InGameEntity target) {
        super(action, countdown, smallDescription, description, turnManager);
        this.target = target;
    }

    public InGameEntity getTarget() {
        return target;
    }
}
