package com.example.eal.Class.TurnManager;

import com.example.eal.Class.Spell.Ealard_spell.WindSpell.RuneOfWindPillar;

public class CountdownActionRemoveWindPillar extends CountdownAction {
    public CountdownActionRemoveWindPillar(TurnManager turnManager) {
        super(()->{},
                RuneOfWindPillar.PILLAR_LASTING,
                "Remove the wind pillar",
                turnManager);
    }
}
