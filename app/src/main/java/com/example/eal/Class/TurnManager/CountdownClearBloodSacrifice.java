package com.example.eal.Class.TurnManager;

import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Spell.Element.Blood;

public class CountdownClearBloodSacrifice extends CountdownAction{
    public CountdownClearBloodSacrifice(TurnManager turnManager) {
        super(Blood::clearBloodSacrificeList
                ,1,
                "Everyone must again make a blood sacrifice to cast blood spell",
                turnManager);
    }
}
