package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_phoenix_wing_slash;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_phoenix_ethered extends Ethered{
    private static final String NAME = "Fire phoenix";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_phoenix_wing_slash.getSPELLNAME()));


    public Fire_phoenix_ethered() {
        super(6,
                3,
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
