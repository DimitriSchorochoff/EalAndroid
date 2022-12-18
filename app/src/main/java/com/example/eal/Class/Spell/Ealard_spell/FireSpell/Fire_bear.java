package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_bear_ethered;

public class Fire_bear extends ClassicFireSpell {
    private static final String SPELL_NAME = "Fire bear";

    public Fire_bear() {
        super(5,
                new Fire_bear_ethered());
    }

    public static String get_SPELL_NAME(){
        return SPELL_NAME;
    }

    @Override
    public String getSpellName() {
        return SPELL_NAME;
    }
}