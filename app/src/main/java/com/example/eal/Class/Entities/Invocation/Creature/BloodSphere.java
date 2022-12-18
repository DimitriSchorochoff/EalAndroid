package com.example.eal.Class.Entities.Invocation.Creature;

import com.example.eal.Class.Spell.Element.Blood;
import com.example.eal.Class.Spell.Spell;

import java.util.ArrayList;

public class BloodSphere extends Creature {
    private static final String NAME = "Blood sphere";

    private static final ArrayList<String> spellList = Spell.filterByElement(Spell.getListAllEalardSpell(), new Blood());

    public BloodSphere() {
        super(0, 0, 0,
                "Blood sphere are summoned whenever a blood sacrifice is made and the caster doesn't have a sphere yet." +
                        "It will move in same direction as it's owner and cast blood spell in same direction but with different damage." +
                        "The blood sphere damage depends on it's Vitality.", spellList);
    }

    public static String getNAME(){
        return NAME;
    }
    @Override
    public String getName() {
        return NAME;
    }
}
