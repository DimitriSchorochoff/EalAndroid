package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_eagle_strike;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_eagle_ethered extends Ethered {
    private static final String NAME = "Fire eagle";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_eagle_strike.getSPELLNAME()));


    public Fire_eagle_ethered() {
        super(3,
                5,
                "",
                spellList);
    }


    public static String getNAME(){
        return NAME;
    }
    @Override
    public String getName() {
        return NAME;
    }
}
