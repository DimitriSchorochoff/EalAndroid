package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_lion_roar;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_octopus_juggle;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_octopus_slam;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_octopus_ethered extends Ethered{
    private static final String NAME = "Fire octopus";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList(Fire_octopus_slam.getSPELLNAME(), Fire_octopus_juggle.getSPELLNAME()));


    public Fire_octopus_ethered() {
        super(8,
                1,
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
