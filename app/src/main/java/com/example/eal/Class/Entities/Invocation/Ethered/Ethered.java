package com.example.eal.Class.Entities.Invocation.Ethered;

import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.Spell.Effect;

import java.util.ArrayList;

public abstract class Ethered extends Invocation {
    public Ethered(int base_energy, int base_mobility, String additionalInformation, ArrayList<String> baseSpellList){
        super(0,
                base_energy,
                base_mobility,
                false,
                false,
                false,
                Effect.ETHERED,
                "Ethered are invulnerable invocation, you can't target them, walk on them but you can see though them.",
                additionalInformation,
                baseSpellList,
                EntityType.ETHERED);
    }
}