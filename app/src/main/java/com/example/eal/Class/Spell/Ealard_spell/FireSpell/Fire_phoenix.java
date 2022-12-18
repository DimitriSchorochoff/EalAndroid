package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.CountdownActionTargeted;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered.Fire_phoenix_ethered;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;

import java.util.ArrayList;

public class Fire_phoenix extends ClassicFireSpell{
    private static final String SPELLNAME = "Fire phoenix";

    private static ArrayList<InGameEntity> inGameEntitiesWithEnergyReduction = new ArrayList<>();
    private static final int reducedCost = 0;

    public Fire_phoenix(){
        super(6,
                new Fire_phoenix_ethered());
    }

    @Override
    public String getSpellName() {
        return SPELLNAME;
    }

    public static String getSPELLNAME() {
        return SPELLNAME;
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        super.spellEffect(activity, caster, players, turnManager);


        if(getEnergy_cost() != reducedCost){
            Runnable reduceCost = ()->{
                inGameEntitiesWithEnergyReduction.add(caster);

                Runnable restoreCost = ()-> inGameEntitiesWithEnergyReduction.remove(caster);
                //2 on countdown so
                turnManager.addCountdownAction(new CountdownActionTargeted(restoreCost,
                        1,
                        String.format("Restore %s cost of %s: %s to normal",
                                Fire_phoenix.getSPELLNAME(),
                                caster.getInGameSquad().getPlayer().getName(),
                                caster.getName()),
                        turnManager,
                        caster));
            };

            turnManager.addCountdownAction(new CountdownActionTargeted(reduceCost, 1,
                    String.format("Reduce %s cost of %s",
                            Fire_phoenix.getSPELLNAME(),
                            caster.getCompleteName()),
                    turnManager,
                    caster));
        }
    }

    @Override
    public int getEnergy_cost() {
        if(inGameEntitiesWithEnergyReduction.contains(getOwner()))
            return reducedCost;
        else
            return super.getEnergy_cost();
    }
}