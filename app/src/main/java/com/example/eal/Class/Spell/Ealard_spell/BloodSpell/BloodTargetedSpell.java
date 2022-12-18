package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameBloodSphere;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Blood;
import com.example.eal.Class.Spell.Element.Element;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Class.Spell.TargetedSpell;
import com.example.eal.Class.TurnManager.TurnManager;

import java.util.ArrayList;

public abstract class BloodTargetedSpell extends TargetedSpell {
    public BloodTargetedSpell(int power, Effect effect, int energy_cost, Area range, Area area, Invocation invocation, String description, int masteryLevel) {
        super(new Blood(), power, effect, energy_cost, range, area, invocation, description, masteryLevel);
    }

    @Override
    public int getPower() {
        if(getOwner() instanceof InGameBloodSphere) {
            return power +
                    getOwner().getVitalityCurrent()/InGameBloodSphere.basicIncreaseVitalityDamageFaction;
        } else
            return power;
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        InGameBloodSphere bs = InGameBloodSphere.getBloodSphere(caster);
        if(bs != null){
            Spell bloodSpell = Spell.getSpell(getSpellName());
            bloodSpell.setOwner(bs);
            bloodSpell.extra_cast(activity, bs, players, turnManager);
        }

        if(!getTargets().isEmpty()){
            Blood element = (Blood) getElement();
            element.makeBloodSacrifice(activity, caster, turnManager);
        }
    }
}
