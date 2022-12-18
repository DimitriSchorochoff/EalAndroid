package com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_wolf_ethered;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Fire;
import com.example.eal.Class.Spell.Invocation_spell.Invocation_spell;

import java.util.ArrayList;

public class Fire_wolf_bite extends FireInvocationTargetedSpell implements Invocation_spell {
    private static final String SPELL_NAME = "Wolf bite";

    private static final int increase_damage_by_turn = 3;

    public Fire_wolf_bite() {
        super(new Fire(),
                12,
                Effect.DAMAGE,
                4,
                new Area(1,1, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                "Deal damage, these increase each turn a target is hit in a row",
                0);
    }

    public int getWolfBiteDamage(InGameEntity target){
        return getPower() + Fire_wolf_ethered.getInGameEntitiesHitInRow().getOrDefault(target, 0) * increase_damage_by_turn;
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

        //TODO
        /*
        for(InGameEntity target: getTargets()){
            target.takeDamage(getWolfBiteDamage(target));
        }


        ArrayList<CountdownAction> countdownActions = turnManager.getCountdownActions();
        Predicate<CountdownAction> isCountdownWolfActionAndShareTarget =
                (c)-> c instanceof CountdownWolfAction && getTargets().contains(((CountdownWolfAction) c).getTarget());

        ArrayList<CountdownAction> actionsToRemove = countdownActions.stream()
                .filter(isCountdownWolfActionAndShareTarget)
                .collect(Collectors.toCollection(ArrayList::new));

        countdownActions.removeAll(actionsToRemove);

        for (InGameEntity target: getTargets()){
            turnManager.addCountdownAction(new CountdownActionHitByWolf(target, turnManager));
        }

         */
    }
}
