package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.CountdownActionRemoveInvocation;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_elephant_ethered;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameFireInvocation.InGameInvocationFireElephantEthered;
import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;

import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Player;

import java.util.ArrayList;

public class Fire_elephant extends ClassicFireSpell {
    private static final String SPELL_NAME = "Fire elephant";

    public Fire_elephant() {
        super(5,
                new Fire_elephant_ethered());
    }

    public static String get_SPELL_NAME(){
        return SPELL_NAME;
    }

    @Override
    public String getSpellName() {
        return SPELL_NAME;
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        InGameInvocation inGameInvocation = summonElephant(caster);
        turnManager.addCountdownAction(new CountdownActionRemoveInvocation(inGameInvocation, 1, turnManager));
    }

    private InGameInvocation summonElephant(InGameEntity caster){
        InGameSquad squad = caster.getInGameSquad();
        InGameInvocationFireElephantEthered returnInvocation = new InGameInvocationFireElephantEthered((Fire_elephant_ethered) getInvocation(), caster);
        squad.addInGameEntity(returnInvocation);

        return returnInvocation;
    }
}
