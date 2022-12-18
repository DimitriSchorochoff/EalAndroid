package com.example.eal.Class.InGame.InGameInvocation.InGameFireInvocation;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_elephant_ethered;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Spell.Spell;

public class InGameInvocationFireElephantEthered extends InGameFireInvocation {
    Fire_elephant_ethered fire_elephant_ethered;

    public InGameInvocationFireElephantEthered(Fire_elephant_ethered invocation, InGameEntity summoner) {
        super(invocation, summoner);

        this.fire_elephant_ethered = (Fire_elephant_ethered) getInvocation();
    }

    @Override
    public void takeDamage(int damage) {
        Spell spell = Spell.getSpell(fire_elephant_ethered.getSpellList().get(0));
        spell.setOwner(this);
        spell.extra_cast(App.getActivity(), this, PlayInGameActivity.getCurrentGamePlayers(), PlayInGameActivity.getCurrentGameTurnManager());
    }
}
