package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameBloodSphere;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Blood;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class Union extends Spell implements MessageDialog.Listener{
    private static final String SPELLNAME = "Union";

    public Union() {
        super(new Blood(),
                0,
                Effect.SHIELD,
                3,
                new Area(0, 0, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                "Destroy you blood sphere and use it's vitality as shield.",
                1);
    }

    @Override
    public void cast(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        if(InGameBloodSphere.getBloodSphere(getOwner()) == null){
            MessageDialog dialog = MessageDialog.newInstance(this);
            dialog.show(activity.getSupportFragmentManager(), null);
        } else {
            super.cast(activity, caster, players, turnManager);
        }
    }

    @Override
    public int getPower() {
        InGameBloodSphere bs = InGameBloodSphere.getBloodSphere(getOwner());
        if(bs != null)
            return bs.getVitalityCurrent();
        else
            return power;

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
        InGameBloodSphere bs = InGameBloodSphere.getBloodSphere(getOwner());
        //Case where bs is null is treated in cast.
        caster.gainShield(bs.getVitalityCurrent());
        bs.die();
    }

    @Override
    public String getMessage() {
        return "You don't have any blood sphere.";
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {

    }
}
