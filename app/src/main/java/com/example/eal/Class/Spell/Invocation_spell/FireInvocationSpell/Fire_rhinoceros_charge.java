package com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Fire;
import com.example.eal.Class.Spell.Invocation_spell.Invocation_spell;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class Fire_rhinoceros_charge extends FireInvocationTargetedSpell implements Invocation_spell, MessageDialog.Listener{
    private static final String SPELL_NAME = "Rhinoceros charge";
    private static final int pushStrength = 1;

    public Fire_rhinoceros_charge() {
        super(new Fire(),
                12,
                Effect.DAMAGE,
                4,
                new Area(1,1, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                "Deal damage and push target",
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

        if(!getTargets().isEmpty()) {
            MessageDialog dialog = MessageDialog.newInstance(this);
            dialog.show(activity.getSupportFragmentManager(), null);
        }

        dealDamageToTargets(getPower(), getTargets());
    }

    @Override
    public String getMessage() {
        return String.format("Push %s by %d case", getTargetsNames(), pushStrength);
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {
    }
}
