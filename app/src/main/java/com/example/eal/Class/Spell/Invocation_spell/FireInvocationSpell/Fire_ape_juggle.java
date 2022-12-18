package com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_ethered;
import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Fire;
import com.example.eal.Class.Spell.Invocation_spell.Invocation_spell;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class Fire_ape_juggle extends FireInvocationTargetedSpell implements Invocation_spell, MessageDialog.Listener {
    private static final String SPELL_NAME = "Ape juggle";

    public Fire_ape_juggle() {
        super(new Fire(),
                0,
                Effect.DISPLACEMENT,
                4,
                new Area(1,1, Area.AreaType.STAR),
                new Area(0,0, Area.AreaType.CLASSIC),
                null,
                "If possible move target symetricly around him. Can target all invocation.",
                0);

        setTargetFilter((i)-> i instanceof InGameInvocation || i.isSpellTarget());
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
    }

    @Override
    public String getMessage() {
        return String.format("If possible move %s symetricly around the ape.", getTargetsNames());
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {
    }
}
