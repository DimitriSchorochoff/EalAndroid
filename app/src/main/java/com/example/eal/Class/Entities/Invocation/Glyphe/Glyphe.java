package com.example.eal.Class.Entities.Invocation.Glyphe;

import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.Spell.Effect;

import java.util.ArrayList;

public abstract class Glyphe extends Invocation {
    public Glyphe(int base_energy, int base_mobility, String additionalInformation, ArrayList<String> baseSpellList){
        super(0,
                base_energy,
                base_mobility,
                false,
                true,
                false,
                Effect.GLYPHE,
                "Glyphe are invulnerable invocation. Any entity can walk onto a glyphe but it will trigger it's spell. The same is true if the glyphe walk under an entity",
                additionalInformation,
                baseSpellList,
                EntityType.GLYPHE);
    }
}
