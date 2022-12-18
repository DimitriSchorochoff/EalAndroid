package com.example.eal.Class.Entities;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Entity implements Serializable {
    protected int base_vitality;
    protected int base_energy;
    protected int base_mobility;

    protected boolean isSpellTarget;
    protected boolean isWalkable;
    protected boolean isVisionRestraining;

    protected EntityType entityType;

    public Entity(int base_vitality, int base_energy, int base_mobility, boolean isSpellTarget, boolean isWalkable, boolean isVisionRestraining, EntityType entityType){
        this.base_vitality = base_vitality;
        this.base_energy = base_energy;
        this.base_mobility = base_mobility;
        this.isSpellTarget = isSpellTarget;
        this.isWalkable = isWalkable;
        this.isVisionRestraining = isVisionRestraining;

        this.entityType = entityType;
    }

    public abstract String getName();

    public abstract ArrayList<String> getSpellList();

    public int getBase_vitality() {
        return base_vitality;
    }

    public int getBase_mobility() {
        return base_mobility;
    }

    public int getBase_energy() {
        return base_energy;
    }

    public boolean isSpellTarget() {
        return isSpellTarget;
    }

    public boolean isVisionRestraining() {
        return isVisionRestraining;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public enum EntityType implements Serializable{
        EALARD,
        GLYPHE,
        ETHERED,
        CREATURE
    }
}
