package com.example.eal.Class.InGame.InGameInvocation;

import com.example.eal.Class.Entities.Invocation.Creature.BloodSphere;
import com.example.eal.Class.InGame.InGameEntity;

import java.util.HashMap;

public class InGameBloodSphere extends InGameInvocation{
    private static HashMap<InGameEntity, InGameBloodSphere> bloodSphereSummoners = new HashMap<>();
    public static final int vitalityBuffByBloodSacrifice = 10;

    public static final int basicDamageRank = 2;
    public static final int basicIncreaseVitalityDamageFaction = 20;

    public InGameBloodSphere(BloodSphere invocation, InGameEntity summoner) {
        super(invocation, summoner);

        bloodSphereSummoners.put(summoner, this);

        setName((String.format("%s's %s", summoner.getName(), getName())));
        setVitalityCurrent(0);
        onBloodSacrifice();
    }

    public static InGameBloodSphere getBloodSphere(InGameEntity inGameEntity){
        return bloodSphereSummoners.getOrDefault(inGameEntity, null);
    }

    public void onBloodSacrifice(){
        setVitalityCurrent(getVitalityCurrent()+vitalityBuffByBloodSacrifice);
    }

    @Override
    public int getVitalityMax() {
        //No vitality max is needed
        return -1;
    }

    @Override
    public int getVitalityCurrent() {
        return super.getVitalityCurrent();
    }

    @Override
    public boolean isDoneForRound() {
        return true;
    }

    @Override
    public void die() {
        super.die();
        bloodSphereSummoners.remove(getSummoner());
    }
}
