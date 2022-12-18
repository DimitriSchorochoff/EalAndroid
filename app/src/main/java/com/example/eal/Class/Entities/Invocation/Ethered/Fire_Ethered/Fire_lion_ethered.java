package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_lion_roar;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_lion_ethered extends Ethered{
    private static final String NAME = "Fire lion";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_lion_roar.getSPELLNAME()));


    public Fire_lion_ethered() {
        super(5,
                2,
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
