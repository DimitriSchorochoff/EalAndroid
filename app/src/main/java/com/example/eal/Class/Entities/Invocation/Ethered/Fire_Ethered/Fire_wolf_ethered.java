package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_wolf_bite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Fire_wolf_ethered extends Ethered{
    private static final String NAME = "Fire wolf";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_wolf_bite.getSPELLNAME()));

    private static HashMap<InGameEntity, Integer> inGameEntitiesHitInRow = new HashMap<>();


    public Fire_wolf_ethered() {
        super(4,
                2,
                "",
                spellList);
    }

    public static HashMap<InGameEntity, Integer> getInGameEntitiesHitInRow() {
        return inGameEntitiesHitInRow;
    }

    public static String getNAME(){
        return NAME;
    }
    @Override
    public String getName() {
        return NAME;
    }
}
