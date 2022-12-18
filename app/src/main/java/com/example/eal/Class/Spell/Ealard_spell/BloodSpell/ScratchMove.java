package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameBloodSphere;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Blood;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Class.Spell.TargetedSpell;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class ScratchMove extends BloodTargetedSpell implements MessageDialog.Listener {
    private static final String SPELLNAME = "Scratch/Move";

    public ScratchMove() {
        super(2,
                Effect.DAMAGE,
                1,
                new Area(1, 1, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                "Make small damage. If sphere doesn't hit, it move instead",
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

        if(caster instanceof InGameBloodSphere && getTargets().isEmpty()){
            MessageDialog dialog = MessageDialog.newInstance(this);
            dialog.show(activity.getSupportFragmentManager(), null);
        }
    }

    @Override
    public String getMessage() {
        return String.format("Move %s on empty targeted case", getTargetsNames());
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {}
}
