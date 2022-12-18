package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_rhinoceros_ethered;

public class Fire_rhinoceros extends ClassicFireSpell{
    private static final String SPELL_NAME = "Fire rhinoceros";

    public Fire_rhinoceros() {
        super(4,
                new Fire_rhinoceros_ethered());
    }

    public static String get_SPELL_NAME(){
        return SPELL_NAME;
    }

    @Override
    public String getSpellName() {
        return SPELL_NAME;
    }
}
