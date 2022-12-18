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

public class Fire_octopus_slam extends FireInvocationTargetedSpell implements Invocation_spell{
    private static final String SPELL_NAME = "Octopus slam";

    public Fire_octopus_slam() {
        super(new Fire(),
                16,
                Effect.DAMAGE,
                8,
                new Area(0,0, Area.AreaType.CLASSIC),
                new Area(1, 2, Area.AreaType.STAR),
                null,
                "Deal damage in a star",
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
