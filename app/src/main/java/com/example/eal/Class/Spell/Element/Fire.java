package com.example.eal.Class.Spell.Element;

import androidx.annotation.Nullable;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Class.TurnManager.CountdownActionRemoveInvocation;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameFireInvocation.InGameFireInvocation;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Map.Map;

public class Fire extends Element{
    private static final String ELEMENT = "Fire";

    @Override
    public String getElementName() {
        return ELEMENT;
    }

    public static String getELEMENT() {
        return ELEMENT;
    }

    public void summonFireInvocation(InGameEntity caster, Invocation invocation, TurnManager turnManager, int countDown){
        InGameSquad squad = caster.getInGameSquad();
        InGameFireInvocation summonedInvocation = new InGameFireInvocation(invocation, caster);
        squad.addInGameEntity(summonedInvocation);

        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s has been summoned", summonedInvocation.getName()), Map.EFFECT_INDENTATION);

        turnManager.addCountdownAction(new CountdownActionRemoveInvocation(summonedInvocation, countDown, turnManager));
    }

    public void summonFireInvocation(InGameEntity caster, Invocation invocation, TurnManager turnManager){
        InGameSquad squad = caster.getInGameSquad();
        InGameFireInvocation summonedInvocation = new InGameFireInvocation(invocation, caster);
        squad.addInGameEntity(summonedInvocation);

        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s has been summoned", summonedInvocation.getName()), Map.EFFECT_INDENTATION);

        turnManager.addCountdownAction(new CountdownActionRemoveInvocation(summonedInvocation, 1, turnManager));
    }
}
