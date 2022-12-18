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

public class RuneOfExplosion extends TargetedSpell {
    private static final String SPELLNAME = "Rune of explosion";

    public RuneOfExplosion() {
        super(new Wind(),
                10,
                Effect.DAMAGE,
                3,
                new Area(0, 0, Area.AreaType.CLASSIC),
                new Area(1, 2, Area.AreaType.CLASSIC),
                null,
                "Deal damage",
                0);
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
