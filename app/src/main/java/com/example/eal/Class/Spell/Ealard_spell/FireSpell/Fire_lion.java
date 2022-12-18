package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_lion_ethered;

public class Fire_lion extends ClassicFireSpell{
    private static final String SPELLNAME = "Fire lion";

    public Fire_lion(){
        super(5,
                new Fire_lion_ethered());
    }

    @Override
    public String getSpellName() {
        return SPELLNAME;
    }

    public static String getSPELLNAME() {
        return SPELLNAME;
    }
}
