package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_bear_slash;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_bear_ethered extends Ethered {
    private static final String NAME = "Fire bear";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_bear_slash.getSPELLNAME()));


    public Fire_bear_ethered() {
        super(5,
                0,
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
