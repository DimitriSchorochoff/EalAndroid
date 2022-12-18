package com.example.eal.Class.InGame;

import com.example.eal.Class.Entities.Ealard;
import com.example.eal.AdditionnalRessource.AdditionalFunction;

import java.util.ArrayList;
import java.util.Objects;

public class InGameEalard extends InGameEntity {

    public InGameEalard(Ealard ealard, InGameSquad inGameSquad){
        super((Ealard) AdditionalFunction.deepCopy(ealard), inGameSquad);

        setCurrentToMax();
    }

    public Ealard getEalard() {
        return (Ealard) modelEntity;
    }

    @Override
    public void setName(String name){
        this.getEalard().setName(name);
    }


    @Override
    public int getVitalityMax() {
        return Ealard.getVitalityMax(getEalard().getVitality());
    }

    @Override
    public int getEnergyMax() {
        return Ealard.getEnergyMax(getEalard().getEnergy());
    }

    @Override
    public int getMobilityMax() {
        return Ealard.getMobilityMax(getEalard().getMobility());
    }

    @Override
    public void endTurn(){
        super.endTurn();
        inGameSquad.endTurn();
    }

    @Override
    public void die() {
        if(isPuppet()){
            if(!useEnergyAndMobilityToIncreaseVitality())
                super.die();
        } else{
            super.die();
        }
    }

    public boolean puppetIncVitality(){
        if(getEalard().incVitality()){
            vitalityCurrent += Ealard.getGain_vitality();
            return true;
        }
        return false;
    }

    public boolean puppetIncEnergy(){
        if(getEalard().incEnergy()){
            energyCurrent += Ealard.getGain_energy();
            return true;
        }
        return false;
    }

    public boolean puppetIncMobility(){
        if(getEalard().incMobility()){
            mobilityCurrent += Ealard.getGain_mobility();
            return true;
        }
        return false;
    }

    public boolean puppetDecVitality(){
        if(getVitalityMax() >= vitalityShown+Ealard.getGain_vitality()){
            getEalard().changeVitality(-1);
            vitalityCurrent-= Ealard.getGain_vitality();
            return true;
        }
        return false;
    }

    public boolean puppetDecEnergy(){
        if(getEnergyMax() > energyShown + Ealard.getGain_energy()){
            getEalard().changeEnergy(-1);
            energyCurrent-= Ealard.getGain_energy();
            return true;
        }
        return false;
    }

    public boolean puppetDecMobility(){
        if(getMobilityMax() > mobilityShown + Ealard.getGain_mobility()){
            getEalard().changeMobility(-1);
            mobilityCurrent-= Ealard.getGain_mobility();
            return true;
        }
        return false;
    }

    public boolean useEnergyAndMobilityToIncreaseVitality(){
        while(vitalityCurrent <= 0){
            if(puppetIncVitality()){}
            else{
                if(puppetDecEnergy()){}
                else if (puppetDecMobility()){}
                else
                    return false;
            }
        }
        updateVitalityShown();
        return true;
    }
}
