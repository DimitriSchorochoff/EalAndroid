package com.example.eal.Class.TurnManager.CountdownWolfAction;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_wolf_ethered;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_wolf_bite;

import java.util.HashMap;

public class CountdownActionHitByWolf extends CountdownWolfAction {
    public CountdownActionHitByWolf(InGameEntity target, TurnManager turnManager) {
        super(()->{
                    HashMap<InGameEntity, Integer> hitInRow = Fire_wolf_ethered.getInGameEntitiesHitInRow();
                    hitInRow.put(target, hitInRow.getOrDefault(target, 0)+1);

                    turnManager.addCountdownAction(new CountdownActionNotHitByWolf(target, turnManager));
                },
                String.format("Increase %s damage to %d on %s",
                        Fire_wolf_ethered.getNAME(),
                        (new Fire_wolf_bite()).getWolfBiteDamage(target),
                        target.getCompleteName()),
                target,
                turnManager);
    }
}
