package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_ape_juggle;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_octopus_juggle;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_octopus_slam;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_ape_ethered extends Ethered{
    private static final String NAME = "Fire ape";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_ape_juggle.getSPELLNAME()));


    public Fire_ape_ethered() {
        super(4,
                4,
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
