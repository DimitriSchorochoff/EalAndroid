package com.example.eal.Class.Spell.Ealard_spell.WindSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Wind;
import com.example.eal.Class.Spell.TargetedSpell;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class RuneOfProjection extends TargetedSpell implements MessageDialog.Listener {
    private static final String SPELLNAME = "Rune of projection";

    private static final int KNOCKBACK_POWER = 2;

    public RuneOfProjection() {
        super(new Wind(),
                15,
                Effect.DAMAGE,
                5,
                new Area(0, 0, Area.AreaType.CLASSIC),
                new Area(1, 3, Area.AreaType.STAR),
                null,
                "Deal damage and knockback",
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

        if(!getTargets().isEmpty()) {
            MessageDialog dialog = MessageDialog.newInstance(this);
            dialog.show(activity.getSupportFragmentManager(), null);
        }
    }

    @Override
    public String getMessage() {
        return String.format("Knockback %s by %d case away from runned object", getTargetsNames(), KNOCKBACK_POWER);
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {}
}
