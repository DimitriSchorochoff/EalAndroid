package com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_enhance;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Element;
import com.example.eal.Class.Spell.TargetedSpell;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public abstract class FireInvocationTargetedSpell extends TargetedSpell{
    private MessageDialog.Listener dieListener;

    public FireInvocationTargetedSpell(Element element, int power, Effect effect, int energy_cost, Area range, Area area, Invocation invocation, String description, int masteryLevel) {
        super(element, power, effect, energy_cost, range, area, invocation, description, masteryLevel);

        dieListener = new MessageDialog.Listener() {
            @Override
            public String getMessage() {
                return String.format("Remove %s from the board", getOwner().getCompleteName());
            }

            @Override
            public String getAdditionalMessage() {
                return null;
            }

            @Override
            public void onQuitMessageDialog() {getOwner().die();}
        };
    }


    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        MessageDialog dieDialog = MessageDialog.newInstance(dieListener);
        dieDialog.show(activity.getSupportFragmentManager(), null);
    }

    @Override
    public int getPower() {
        if(Fire_enhance.getEnhancedInvocation().contains(getOwner()))
            return super.getPower() + Fire_enhance.getPower_boost();
        else
            return super.getPower();
    }
}
