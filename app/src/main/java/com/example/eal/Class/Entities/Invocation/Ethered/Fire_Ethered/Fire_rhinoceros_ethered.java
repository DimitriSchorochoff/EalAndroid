package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_rhinoceros_charge;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_rhinoceros_ethered extends Ethered{
    private static final String NAME = "Fire rhinoceros";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_rhinoceros_charge.getSPELLNAME()));


    public Fire_rhinoceros_ethered() {
        super(4,
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
