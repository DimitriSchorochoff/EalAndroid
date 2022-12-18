package com.example.eal.Class.Spell.Ealard_spell.BloodSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Blood;
import com.example.eal.Class.Spell.Spell;
import com.example.eal.Class.TurnManager.TurnManager;

import java.util.ArrayList;

public class BloodReverse extends Spell {
    private static final String SPELLNAME = "Blood reverse";

    public BloodReverse() {
        super(new Blood(),
                0,
                Effect.OTHER,
                2,
                new Area(0, 0, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                null,
                "From now on, your sphere move and cast spell in opposite direction",
                0);
    }


    @Override
    public String getSpellName() {
        return SPELLNAME;
    }

    public static String getSPELLNAME(){
        return SPELLNAME;
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s's blood sphere is reversed", caster.getCompleteName()), Map.SPELL_AND_ACTION_INDENTATION);
    }
}
