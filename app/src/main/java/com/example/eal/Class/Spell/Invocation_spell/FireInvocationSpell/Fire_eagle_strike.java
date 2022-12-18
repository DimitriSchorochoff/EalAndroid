package com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Fire;
import com.example.eal.Class.Spell.Invocation_spell.Invocation_spell;

import java.util.ArrayList;

public class Fire_eagle_strike extends FireInvocationTargetedSpell implements Invocation_spell {
    private static final String SPELL_NAME = "Eagle strike";

    public Fire_eagle_strike() {
        super(new Fire(),
                6,
                Effect.DAMAGE,
                3,
                new Area(1,1, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                "Deal damage",
                0);
    }

    public static String getSPELLNAME(){
        return SPELL_NAME;
    }

    @Override
    public String getSpellName() {
        return SPELL_NAME;
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        super.spellEffect(activity, caster, players, turnManager);

        dealDamageToTargets(getPower(), getTargets());
    }
}
