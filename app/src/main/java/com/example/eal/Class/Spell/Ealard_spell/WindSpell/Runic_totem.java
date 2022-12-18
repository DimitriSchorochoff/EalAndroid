package com.example.eal.Class.Spell.Ealard_spell.WindSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;
import com.example.eal.Class.TurnManager.CountdownActionRemoveInvocation;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Ethered.Runic_totem_ethered;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Wind;
import com.example.eal.Class.Spell.Spell;

import java.util.ArrayList;

public class Runic_totem extends Spell {
    private static final String SPELLNAME = "Runic totem";

    public Runic_totem() {
        super(new Wind(),
                0,
                Effect.ETHERED,
                2,
                new Area(1, 1, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                new Runic_totem_ethered(),
                String.format("Summon a %s or animate the existing one", Runic_totem_ethered.getNAME()),
                Spell.MASTERY_VALUE);
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
        if(! caster.getInGameSquad().containsRunicTotemEthered()){
            InGameInvocation totem = summonInvocation(caster, getInvocation());
            turnManager.addCountdownAction(new CountdownActionRemoveInvocation(totem, 1, turnManager));
        }
        else
            caster.gainEnergy(getEnergy_cost());
    }
}
