package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class ArcDepression extends BloodTargetedSpell implements MessageDialog.Listener {
    private static final String SPELLNAME = "Arc depression";

    private static final int PULL_POWER = 1;

    public ArcDepression() {
        super(12,
                Effect.DAMAGE,
                4,
                new Area(2, 2, Area.AreaType.CROSS),
                new Area(0, 1, Area.AreaType.ARC_REVERSE),
                null,
                String.format("You deal damage then pull targets by %d case", PULL_POWER),
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

    @Override
    public String getMessage() {
        return String.format("Pull %s by %d case", getTargetsNames(), PULL_POWER);
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {}
}
