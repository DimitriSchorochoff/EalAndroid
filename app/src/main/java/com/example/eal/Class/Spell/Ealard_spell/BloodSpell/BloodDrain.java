package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameBloodSphere;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Blood;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Class.Spell.TargetedSpell;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;
import java.util.function.Predicate;

public class BloodDrain extends TargetedSpell {
    private static final String SPELLNAME = "Blood drain";

    public BloodDrain() {
        super(new Blood(),
                0,
                Effect.OTHER,
                2,
                new Area(0, 0, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                "Casted on yourself, you make a blood ritual. Casted on a sphere, you drain it's hp making an inversed blood ritual.",
                Spell.MASTERY_VALUE);

        setTargetFilter((i)-> (i instanceof  InGameBloodSphere) || i == getOwner());
    }

    @Override
    public int getPower() {
        InGameBloodSphere bs = InGameBloodSphere.getBloodSphere(getOwner());
        if(bs != null)
            return bs.getVitalityCurrent();
        else
            return power;

    }

    public static String getSPELLNAME(){
        return SPELLNAME;
    }

    @Override
    public String getSpellName() {
        return SPELLNAME;
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        InGameBloodSphere bs = InGameBloodSphere.getBloodSphere(getOwner());
        //Case where bs is null is treated in cast.
        caster.gainShield(bs.getVitalityCurrent());
        bs.die();
    }
}
