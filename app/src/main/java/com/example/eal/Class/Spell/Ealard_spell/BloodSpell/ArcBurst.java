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

public class ArcBurst extends BloodTargetedSpell implements MessageDialog.Listener {
    private static final String SPELLNAME = "Arc burst";

    private static final int PUSH_POWER = 1;

    public ArcBurst() {
        super(12,
                Effect.DAMAGE,
                4,
                new Area(2, 2, Area.AreaType.CROSS),
                new Area(0, 1, Area.AreaType.ARC),
                null,
                String.format("You deal damage then push targets by %d case", PUSH_POWER),
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
        return String.format("Push %s by %d case", getTargetsNames(), PUSH_POWER);
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {}
}
