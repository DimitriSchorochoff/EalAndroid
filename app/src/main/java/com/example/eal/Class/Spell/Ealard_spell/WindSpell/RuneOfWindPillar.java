package com.example.eal.Class.Spell.Ealard_spell.WindSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Wind;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Class.TurnManager.CountdownActionRemoveWindPillar;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class RuneOfWindPillar extends Spell implements MessageDialog.Listener{
    private static final String SPELLNAME = "Rune of wind pillar";
    public static final int PILLAR_LASTING = 1;

    public RuneOfWindPillar() {
        super(new Wind(),
                0,
                Effect.OTHER,
                4,
                new Area(3, 4, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                String.format("Place a wind pillar that last %d turn", PILLAR_LASTING),
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
        MessageDialog dialog = MessageDialog.newInstance(this);
        dialog.show(activity.getSupportFragmentManager(), null);

        turnManager.addCountdownAction(new CountdownActionRemoveWindPillar(turnManager));
    }

    @Override
    public String getMessage() {
        return String.format("Place a wind pillar");
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {}
}
