package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameBloodSphere;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class Splash extends BloodTargetedSpell {
    private static final String SPELLNAME = "Splash";

    public Splash() {
        super(12,
                Effect.DAMAGE,
                4,
                new Area(0, 0, Area.AreaType.CLASSIC),
                new Area(1, 1, Area.AreaType.CROSS),
                null,
                "Make damage in cross",
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

        super.spellEffect(activity, caster, players, turnManager);
    }
}
