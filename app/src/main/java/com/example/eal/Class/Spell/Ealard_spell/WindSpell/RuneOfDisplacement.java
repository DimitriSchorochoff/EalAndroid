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

public class RuneOfDisplacement extends TargetedSpell implements MessageDialog.Listener{
    private static final String SPELLNAME = "Rune of displacement";

    private static final int DISPLACEMENT_POWER = 1;

    public RuneOfDisplacement() {
        super(new Wind(),
                DISPLACEMENT_POWER,
                Effect.DISPLACEMENT,
                3,
                new Area(1, 3, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                String.format("Move target by %d case in any direction",DISPLACEMENT_POWER),
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
        if(!getTargets().isEmpty()) {
            MessageDialog dialog = MessageDialog.newInstance(this);
            dialog.show(activity.getSupportFragmentManager(), null);
        }
    }

    @Override
    public String getMessage() {
        return String.format("Move %s by %d case in any direction", getTargetsNames(), DISPLACEMENT_POWER);
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {}
}
