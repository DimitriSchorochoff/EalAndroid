package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.TurnManager.TurnManager;

import java.util.ArrayList;

public class BloodRitual extends BloodTargetedSpell {
    private static final String SPELLNAME = "Blood ritual";
    public static final int MOVEMENT_LOSS = 1;

    private static ArrayList<InGameEntity> preparedCaster = new ArrayList<>();

    public BloodRitual() {
        super(12,
                Effect.MOBILITY_LOSS,
                3,
                new Area(1, 1, Area.AreaType.DIAGONAL),
                new Area(0,2, Area.AreaType.CLASSIC),
                null,
                String.format("On first cast, you prepare the ritual." +
                        " If ritual is prepared, you deal damage and substract %d movement, ritual is now unprepared.", MOVEMENT_LOSS),
                0);
    }

    @Override
    public String getSpellName() {
        return SPELLNAME;
    }

    public String getSPELLNAME(){
        return SPELLNAME;
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        super.spellEffect(activity, caster, players, turnManager);

        if(preparedCaster.contains(caster)){
            dealDamageToTargets(power, getTargets());
            loseMobilityToTargets(MOVEMENT_LOSS, getTargets());
            preparedCaster.remove(caster);
        } else {
            preparedCaster.add(caster);
            PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s prepare his blood ritual", caster.getCompleteName()), Map.SPELL_AND_ACTION_INDENTATION);

        }

    }
}
