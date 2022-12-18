package com.example.eal.Class.Entities.Invocation.Creature;

import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.Spell.Effect;

import java.util.ArrayList;

public abstract class Creature extends Invocation {
    public Creature(int base_vitality, int base_energy, int base_mobility, String additionalInformation, ArrayList<String> baseSpellList) {
        super(base_vitality,
                base_energy,
                base_mobility,
                true,
                false,
                true,
                Effect.CREATURE,
                "Creature are entities similar to ealards, they are targetable, walkable and you can't see through them.",
                additionalInformation,
                baseSpellList,
                EntityType.CREATURE);
    }
}
