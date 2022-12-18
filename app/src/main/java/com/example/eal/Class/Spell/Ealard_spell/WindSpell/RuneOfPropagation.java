package com.example.eal.Class.Spell.Ealard_spell.WindSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Wind;
import com.example.eal.Class.Spell.TargetedSpell;

import java.util.ArrayList;

public class RuneOfPropagation extends TargetedSpell{
    private static final String SPELLNAME = "Rune of propagation";

    public RuneOfPropagation() {
        super(new Wind(),
                10,
                Effect.DAMAGE,
                6,
                new Area(0, 0, Area.AreaType.CLASSIC),
                new Area(1, 2, Area.AreaType.CROSS),
                null,
                "Deal damage. Recast this spell on new target and new object at maximum range",
                0);

        setMultiHit(true);
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
        dealDamageToTargets(getPower(), getTargets());
    }
}
