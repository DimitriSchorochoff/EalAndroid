package com.example.eal.Class.TurnManager.CountdownWolfAction;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_wolf_ethered;
import com.example.eal.Class.InGame.InGameEntity;

import java.util.HashMap;

public class CountdownActionNotHitByWolf extends CountdownWolfAction {
    public CountdownActionNotHitByWolf(InGameEntity target, TurnManager turnManager) {
        super(()->{
                    HashMap<InGameEntity, Integer> hitInRow = Fire_wolf_ethered.getInGameEntitiesHitInRow();
                    hitInRow.remove(target);
                },
                String.format("Restore %s damage on %s", Fire_wolf_ethered.getNAME(), target.getCompleteName()),
                target,
                turnManager);
    }
}
