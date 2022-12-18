package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_ape_ethered;
import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_octopus_ethered;

public class Fire_ape extends ClassicFireSpell{
    private static final String SPELLNAME = "Fire ape";

    public Fire_ape(){
        super(4,
                new Fire_ape_ethered());
    }

    @Override
    public String getSpellName() {
        return SPELLNAME;
    }

    public static String getSPELLNAME() {
        return SPELLNAME;
    }
}
