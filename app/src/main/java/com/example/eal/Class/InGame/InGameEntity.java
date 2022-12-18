package com.example.eal.Class.InGame;

import android.content.Intent;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.Entities.Entity;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Spell.Spell;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public abstract class InGameEntity implements Serializable {
    protected Entity modelEntity;
    protected InGameSquad inGameSquad;

    protected String name;

    protected  ArrayList<String> spellList;
    protected ArrayList<String> spellListShown;

    protected int vitalityCurrent;
    protected int energyCurrent;
    protected int mobilityCurrent;
    protected int shieldCurrent;

    protected int vitalityShown;
    protected int energyShown;
    protected int mobilityShown;

    protected ArrayList<String> alreadyCastedSpell;

    protected boolean onTurn;
    protected boolean doneForRound;
    protected boolean isDead;

    public InGameEntity(Entity modelEntity, InGameSquad inGameSquad){
        this.modelEntity = modelEntity;
        this.inGameSquad = inGameSquad;
        this.name = modelEntity.getName();

        this.spellListShown = new ArrayList<>();

        this.vitalityShown = modelEntity.getBase_vitality();
        this.energyShown = modelEntity.getBase_energy();
        this.mobilityShown = modelEntity.getBase_mobility();

        this.vitalityCurrent = 0;
        this.energyCurrent = 0;
        this.mobilityCurrent = 0;
        this.shieldCurrent = 0;

        this.alreadyCastedSpell = new ArrayList<>();

        this.onTurn = false;
        this.doneForRound = false;
        this.isDead = false;
    }

    public Entity.EntityType getEntityType() {
        return modelEntity.getEntityType();
    }

    public String getName() {
        return name;
    }

    public String getCompleteName(){
        return String.format("%s: %s", getInGameSquad().getPlayer().getName(), getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSpellList() {
        return modelEntity.getSpellList();
    }

    public ArrayList<String> getSpellListShown() {
        return spellListShown;
    }

    public abstract int getVitalityMax();
    public abstract int getEnergyMax();
    public abstract int getMobilityMax();

    public int getVitalityCurrent() {
        return vitalityCurrent;
    }

    public int getEnergyCurrent() {
        return energyCurrent;
    }

    public int getMobilityCurrent() {
        return mobilityCurrent;
    }

    public int getShieldCurrent() {
        return shieldCurrent;
    }

    public int getVitalityShown() {
        return vitalityShown;
    }

    public int getEnergyShown() {
        return energyShown;
    }

    public int getMobilityShown() {
        return mobilityShown;
    }
    public int getShieldShown(){
        return shieldCurrent;
    }

    public void setCurrentToMax(){
        this.vitalityCurrent = getVitalityMax();
        this.energyCurrent = getEnergyMax();
        this.mobilityCurrent = getMobilityMax();
        this.shieldCurrent = 0;
    }

    public void setVitalityCurrent(int vitalityCurrent) {
        this.vitalityCurrent = vitalityCurrent;
    }

    public void setEnergyCurrent(int energyCurrent) {
        this.energyCurrent = energyCurrent;
    }

    public void setMobilityCurrent(int mobilityCurrent) {
        this.mobilityCurrent = mobilityCurrent;
    }

    public void setShieldCurrent(int shieldCurrent) {
        this.shieldCurrent = shieldCurrent;
    }

    public InGameSquad getInGameSquad() {
        return inGameSquad;
    }

    public ArrayList<String> getAlreadyCastedSpell() {
        return alreadyCastedSpell;
    }

    public boolean isWalkable() {
        return modelEntity.isWalkable();
    }

    public boolean isVisionRestraining() {
        return modelEntity.isVisionRestraining();
    }

    public boolean isSpellTarget() {
        return modelEntity.isSpellTarget();
    }

    public boolean isPuppet(){
        return inGameSquad.isPuppetSquad();
    }

    public boolean isOnTurn() {
        return onTurn;
    }

    public boolean isDoneForRound() {
        return doneForRound;
    }

    public void takeDamage(int damage){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s take %d damage", getCompleteName(), damage), Map.EFFECT_INDENTATION);

        shieldCurrent -= damage;

        if(shieldCurrent < 0){
            vitalityCurrent += shieldCurrent;
            shieldCurrent = 0;
        }
        updateVitalityShown();

        if(vitalityCurrent <= 0)
            die();
    }

    public void takeTrueDamage(int damage){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s take %d true damage", getCompleteName(), damage), Map.EFFECT_INDENTATION);

        vitalityCurrent -= damage;
        updateVitalityShown();

        if(vitalityCurrent <= 0)
            die();
    }

    private void loseEnergy(int energyLost){
        energyCurrent -= energyLost;
        updateEnergyShown();
    }

    private void loseMobility(int mobilityLost){
        mobilityCurrent -= mobilityLost;
        updateMobilityShown();
    }

    public void effectLoseEnergy(int energyLost){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s lose %d energy", getCompleteName(), energyLost), Map.EFFECT_INDENTATION);

        loseEnergy(energyLost);
    }

    public void effectLoseMobility(int mobilityLost){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s lose %d mobility", getCompleteName(), mobilityLost), Map.EFFECT_INDENTATION);

        loseMobility(mobilityLost);
    }

    public void spellUseEnergy(int energyLost){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s use %d energy", getCompleteName(), energyLost), Map.SPELL_AND_ACTION_INDENTATION);

        loseEnergy(energyLost);
    }

    public void movementUseMobility(int mobilityLost){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s use %d mobility to move", getCompleteName(), mobilityLost), Map.SPELL_AND_ACTION_INDENTATION);

        loseMobility(mobilityLost);
    }

    public void gainVitality(int vitalityGain){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s gain %d vitality", getCompleteName(), vitalityGain), Map.EFFECT_INDENTATION);

        vitalityCurrent += vitalityGain;
        //VitaMax = -1 means no cap so we avoid it.
        if(vitalityCurrent > getVitalityMax() && getVitalityMax() != -1)
            vitalityCurrent = getVitalityMax();
    }

    public void gainShield(int shieldGain){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s gain %d shield", getCompleteName(), shieldGain), Map.EFFECT_INDENTATION);

        shieldCurrent += shieldGain;
    }
    public void gainEnergy(int energyGain){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s gain %d energy", getCompleteName(), energyGain), Map.EFFECT_INDENTATION);

        energyCurrent += energyGain;
    }

    public void gainMobility(int mobilityGain){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s gain %d mobility", getCompleteName(), mobilityGain), Map.EFFECT_INDENTATION);

        mobilityCurrent += mobilityGain;
    }

    public int getDamageTaken(){
        return getVitalityMax() - getVitalityCurrent();
    }

    public void updateVitalityShown(){
        //In case vitalityCurrent is negative
        int currentTurnVitaShown = Math.min(getDamageTaken(), getVitalityMax());
        vitalityShown = Math.max(vitalityShown, currentTurnVitaShown);
    }

    public void updateEnergyShown(){
        //In case energyCurrent is negative
        int currentTurnEnergyShown = Math.min(getEnergyMax() - getEnergyCurrent(), getEnergyMax());
        energyShown = Math.max(energyShown, currentTurnEnergyShown);
    }

    public void updateMobilityShown(){
        //In case mobilityCurrent is negative
        int currentTurnMobiShown = Math.min(getMobilityMax() - getMobilityCurrent(), getMobilityMax());
        mobilityShown = Math.max(mobilityShown, currentTurnMobiShown);
    }

    public void updateSpellListShown(String spellName){
        if(!spellListShown.contains(spellName))
            spellListShown.add(spellName);
    }

    public boolean canCastSpell(Spell spellToCast){
        return isOnTurn() &&
                !getAlreadyCastedSpell().contains(spellToCast.getSpellName()) &&
                getEnergyCurrent() >= spellToCast.getEnergy_cost();
    }

    public void die(){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s died", getCompleteName()), Map.EFFECT_INDENTATION);

        inGameSquad.removeInGameEntity(this);
        isDead = true;
        PlayInGameActivity.getCurrentGameTurnManager().removeTargetedAction(this);

        if(this.onTurn)
            this.endTurn();
    }

    public void startTurn(){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("Start turn of %s", getCompleteName()), Map.ENTITY_INDENTATION);

        onTurn = true;
        PlayInGameActivity.setActiveInGameEntity(this);
    }

    public void endTurn(){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("End turn of %s", getCompleteName()), Map.ENTITY_INDENTATION);

        onTurn = false;
        doneForRound = true;
        PlayInGameActivity.setActiveInGameEntity(null);

        //Start PlayInGame
        Intent intent = new Intent(App.getContext(), PlayInGameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(intent);

        PlayInGameActivity.checkEndGame();
    }

    public void restore(){
        energyCurrent = getEnergyMax();
        mobilityCurrent = getMobilityMax();
        shieldCurrent = 0;

        alreadyCastedSpell.removeAll(alreadyCastedSpell);

        doneForRound = false;
    }

    public boolean isDead() {
        return isDead;
    }
}
