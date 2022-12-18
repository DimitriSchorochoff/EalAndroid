package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_octopus_ethered;

public class Fire_octopus extends ClassicFireSpell{
    private static final String SPELLNAME = "Fire octopus";

    public Fire_octopus(){
        super(8,
                new Fire_octopus_ethered());
    }

    @Override
    public String getSpellName() {
        return SPELLNAME;
    }

    public static String getSPELLNAME() {
        return SPELLNAME;
    }
}
