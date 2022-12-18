package com.example.eal.Class.Entities.Invocation;

import com.example.eal.Class.Entities.Entity;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Spell.Effect;

import java.util.ArrayList;

public abstract class Invocation extends Entity {
    protected Effect type; //Each invocation type is a specific spell effect
    protected String typeDescription;
    protected String additionalInformation;
    protected ArrayList<String> baseSpellList;

    public Invocation(int base_vitality, int base_energy, int base_mobility, boolean isSpellTarget, boolean isWalkable,
                      boolean isVisionRestraining, Effect type, String typeDescription, String additionalInformation,
                      ArrayList<String> baseSpellList, EntityType entityType) {
        super(base_vitality , base_energy, base_mobility, isSpellTarget, isWalkable, isVisionRestraining, entityType);

        this.type = type;
        this.additionalInformation = additionalInformation;
        this.typeDescription = typeDescription;
        this.baseSpellList = baseSpellList;
    }

    @Override
    public ArrayList<String> getSpellList() {
        return baseSpellList;
    }

    public Effect getType() {
        return type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }
}
