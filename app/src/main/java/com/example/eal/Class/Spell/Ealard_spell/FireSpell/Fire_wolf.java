package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_wolf_ethered;

public class Fire_wolf extends ClassicFireSpell{
    private static final String SPELLNAME = "Fire wolf";

    public Fire_wolf(){
        super(4,
                new Fire_wolf_ethered());
    }

    @Override
    public String getSpellName() {
        return SPELLNAME;
    }

    public static String getSPELLNAME() {
        return SPELLNAME;
    }
}
