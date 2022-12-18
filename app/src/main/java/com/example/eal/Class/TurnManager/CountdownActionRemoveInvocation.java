package com.example.eal.Class.TurnManager;

import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;

public class CountdownActionRemoveInvocation extends CountdownActionTargeted {
    public CountdownActionRemoveInvocation(InGameInvocation invocationToRemove, int countdown, TurnManager turnManager) {
        super(invocationToRemove::die,
                countdown,
                String.format("Remove %s of %s", invocationToRemove.getName(), invocationToRemove.getSummoner().getInGameSquad().getPlayer().getName()),
                turnManager,
                invocationToRemove);
    }
}
