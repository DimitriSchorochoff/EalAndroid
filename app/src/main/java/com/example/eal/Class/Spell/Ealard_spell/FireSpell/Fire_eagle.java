package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_eagle_ethered;

public class Fire_eagle extends ClassicFireSpell{
    private static final String SPELL_NAME = "Fire eagle";

    public Fire_eagle() {
        super(3,
                new Fire_eagle_ethered());
    }

    public static String get_SPELL_NAME(){
        return SPELL_NAME;
    }

    @Override
    public String getSpellName() {
        return SPELL_NAME;
    }
}
