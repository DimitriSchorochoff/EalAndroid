package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameBloodSphere;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.TurnManager.TurnManager;

import java.util.ArrayList;

public class Join extends BloodTargetedSpell {
    private static final String SPELLNAME = "Join";

    public Join() {
        super(9,
                Effect.DAMAGE,
                3,
                new Area(0, 4, Area.AreaType.STAR),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                "Casted on yourself, your sphere come next to you dealing damage to entity it traverse. Casted on your sphere, you join it the same way.",
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
        if(caster instanceof InGameBloodSphere) return;

        dealDamageToTargets(getPower(), getTargets());

        super.spellEffect(activity, caster, players, turnManager);
    }
}
