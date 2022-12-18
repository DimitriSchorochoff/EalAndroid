package com.example.eal.Class.InGame.InGameInvocation;

import com.example.eal.AdditionnalRessource.AdditionalFunction;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameSquad;

import java.util.ArrayList;

public class InGameInvocation extends InGameEntity {
    protected InGameEntity summoner;

    public InGameInvocation(Invocation invocation, InGameEntity summoner) {
        super(invocation, summoner.getInGameSquad());

        this.name = AdditionalFunction.getNextValidNumberedName(summoner.getInGameSquad().getUsedNames(), invocation.getName());
        this.summoner = summoner;
        this.spellListShown = getSpellList();

        setCurrentToMax();
    }

    public Invocation getInvocation(){
        return (Invocation) modelEntity;
    }

    @Override
    public ArrayList<String> getSpellListShown() {
        return getSpellList();
    }

    @Override
    public int getVitalityMax() {
        return getInvocation().getBase_vitality();
    }

    @Override
    public int getEnergyMax() {
        return getInvocation().getBase_energy();
    }

    @Override
    public int getMobilityMax() {
        return getInvocation().getBase_mobility();
    }

    public InGameEntity getSummoner(){
        return summoner;
    }

    @Override
    public void endTurn(){
        super.endTurn();

        if(inGameSquad.isDoneForRound())
            inGameSquad.endTurn();
    }
}
