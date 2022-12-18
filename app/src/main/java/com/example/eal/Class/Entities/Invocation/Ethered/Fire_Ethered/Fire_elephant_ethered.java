package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_elephant_trample;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_elephant_ethered extends Ethered {
    private static final String NAME = "Fire elephant";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_elephant_trample.getSPELLNAME()));


    public Fire_elephant_ethered() {
        super(0,
                3,
                "",
                spellList);
    }

    @Override
    public boolean isSpellTarget() {
        return true;
    }

    public static String getNAME(){
        return NAME;
    }
    @Override
    public String getName() {
        return NAME;
    }
}
