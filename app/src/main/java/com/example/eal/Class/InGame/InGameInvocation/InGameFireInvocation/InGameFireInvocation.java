package com.example.eal.Class.InGame.InGameInvocation.InGameFireInvocation;

import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_enhance;

public class InGameFireInvocation extends InGameInvocation {

    private boolean enhanced;

    public InGameFireInvocation(Invocation invocation, InGameEntity summoner) {
        super(invocation, summoner);
        this.enhanced = false;
    }

    public void on_enhance(){
        if(!enhanced){
            enhanced = true;

            setMobilityCurrent(getMobilityCurrent()+ Fire_enhance.getMobility_boost());
        }
    }

    public boolean isEnhanced() {
        return enhanced;
    }
}
