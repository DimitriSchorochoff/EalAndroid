package com.example.eal.Class.TurnManager.CountdownWolfAction;

import com.example.eal.Class.TurnManager.CountdownActionTargeted;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.InGame.InGameEntity;

public class CountdownWolfAction extends CountdownActionTargeted {

    public CountdownWolfAction(Runnable action, String description, InGameEntity target, TurnManager turnManager) {
        super(action, 1, description, turnManager, target);
    }

    public CountdownWolfAction(Runnable action, String smallDescription, String description, InGameEntity target, TurnManager turnManager) {
        super(action, 1, smallDescription, description, turnManager, target);
    }
}
