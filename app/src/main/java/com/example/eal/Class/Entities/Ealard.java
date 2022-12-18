package com.example.eal.Class.Entities;

import com.example.eal.Database.DBManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Ealard extends Entity implements Serializable {
    public static final String GENERIC_EALARD_NAME = "Ealard";

    //Database Attribute
    private String iDEalard;
    private String iDSquad;
    private String name;
    private int energy;
    private int mobility;
    private int vitality;
    private ArrayList<String> spellList;

    //Class specific attribute
    private static String bufferIDEalard = null;

    private int essenceMax;

    private static int cost_vitality = 1;
    private static int cost_energy = 1;
    private static int cost_mobility = 1;
    private static int cost_spell = 1;

    private static int gain_vitality = 5;
    private static int gain_energy = 1;
    private static int gain_mobility = 1;

    public static final boolean isSpellTarget = true;
    public static final boolean isWalkable = false;
    public static final boolean isVisionRestraining = false;

    public static final int base_vitality = 50;
    public static final int base_energy = 7;
    public static final int base_mobility = 3;


    public Ealard(DBManager dbManager, String iDSquad, String name){
        super(base_vitality, base_energy, base_mobility, isSpellTarget, isWalkable, isVisionRestraining, EntityType.EALARD);
        this.iDEalard = UUID.randomUUID().toString();
        this.iDSquad = iDSquad;
        this.name = name;
        this.energy = 0;
        this.mobility = 0;
        this.vitality = 0;
        this.spellList = new ArrayList<>();

        this.essenceMax = dbManager.getSquadGameMode(iDSquad).getNumberEssencePerEalard();
    }

    public static Ealard copyEalard(DBManager dbManager, String IDEalard, String IDSquad){
        // Create a copy of an Ealard
        Ealard e = dbManager.getEalard(IDEalard);

        e.iDEalard = UUID.randomUUID().toString();
        e.iDSquad = IDSquad;

        return e;
    }

    public Ealard(String iDEalard, String iDSquad, int numberEssenceMax , String Name, int Energy, int Mobility, int Vitality, ArrayList<String> SpellList){
        super(base_vitality, base_energy, base_mobility, isSpellTarget, isWalkable, isVisionRestraining, EntityType.EALARD);

        this.iDEalard = iDEalard;
        this.iDSquad = iDSquad;
        this.name = Name;
        this.energy = Energy;
        this.mobility = Mobility;
        this.vitality = Vitality;
        this.spellList = SpellList;

        this.essenceMax = numberEssenceMax;
    }

    public static Ealard puppetEalard(String name, int numberEssenceMax){
        return new Ealard("", "", numberEssenceMax, name, 0, 0, 0, new ArrayList<>());
    }

    public static int essenceLeft(int numberEssence, int vitality, int energy, int mobility, ArrayList<String> spellList){
        return numberEssence - vitality * cost_vitality - energy * cost_energy - mobility * cost_mobility
                - spellList.size() * cost_spell;
    }

    public static int getVitalityMax(int vitality){
        return base_vitality + vitality * gain_vitality;
    }

    public static int getEnergyMax(int energy){
        return base_energy + energy * gain_energy;
    }

    public static int getMobilityMax(int mobility){
        return base_mobility + mobility * gain_mobility;
    }

    public static int getVitality(int vitalityMax){
        return (vitalityMax - base_vitality)/gain_vitality;
    }

    public static int getEnergy(int energyMax){
        return (energyMax - base_energy)/gain_energy;
    }

    public static int getMobility(int mobilityMax){
        return (mobilityMax - base_mobility)/gain_mobility;
    }

    public static String getBufferIDEalard() {
        return bufferIDEalard;
    }

    public static int getCost_energy() {
        return cost_energy;
    }

    public static int getCost_mobility() {
        return cost_mobility;
    }

    public static int getCost_spell() {
        return cost_spell;
    }

    public static int getCost_vitality() {
        return cost_vitality;
    }

    public static int getGain_energy() {
        return gain_energy;
    }

    public static int getGain_mobility() {
        return gain_mobility;
    }

    public static int getGain_vitality() {
        return gain_vitality;
    }

    public String getiDEalard() {
        return iDEalard;
    }

    public String getiDSquad() {
        return iDSquad;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMobility() {
        return mobility;
    }

    public int getVitality() {
        return vitality;
    }

    public ArrayList<String> getSpellList() {
        return spellList;
    }

    public int getEssence() {
        return essenceLeft(essenceMax, vitality, energy, mobility, spellList);
    }

    public int getEssenceMax() {
        return essenceMax;
    }

    public static void setBufferIDEalard(String bufferIDEalard) {
        Ealard.bufferIDEalard = bufferIDEalard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public void setMobility(int mobility) {
        this.mobility = mobility;
    }


    public void setSpellList(ArrayList<String> spellList) {
        this.spellList = spellList;
    }


    public boolean incVitality(){
        if(getEssence() < getCost_vitality())
            return false;
        else{
            vitality++;
            return true;
        }
    }

    public boolean incEnergy(){
        if(getEssence() < getCost_energy())
            return false;
        else{
            energy++;
            return true;
        }
    }

    public boolean incMobility(){
        if(getEssence() < getCost_mobility())
            return false;
        else{
            mobility++;
            return true;
        }
    }

    public void changeVitality(int valueToAdd){
        vitality += valueToAdd;
    }

    public void changeEnergy(int valueToAdd){
        energy += valueToAdd;
    }
    public void changeMobility(int valueToAdd){
        mobility += valueToAdd;
    }


    public boolean addSpell(String spellName){
        if (getEssence() < getCost_spell())
            return false;
        else {
            getSpellList().add(spellName);
            return true;
        }
    }
}