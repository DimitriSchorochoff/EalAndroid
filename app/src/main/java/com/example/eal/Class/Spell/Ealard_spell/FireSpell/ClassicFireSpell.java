package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Ealard_spell.Ealard_spell;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Fire;
import com.example.eal.Class.Spell.Spell;

import java.util.ArrayList;

public abstract class ClassicFireSpell extends Spell implements Ealard_spell {
    Invocation fire_invocation;

    public ClassicFireSpell(int energy_cost, Invocation invocation) {
        super(new Fire(),
                0,
                Effect.ETHERED,
                energy_cost,
                new Area(1,1, Area.AreaType.CLASSIC),
                new Area(0, 0, Area.AreaType.CLASSIC),
                invocation,
                String.format("Summon a %s", invocation.getName()),
                0);

        fire_invocation = invocation;
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        Fire element = (Fire) getElement();
        element.summonFireInvocation(caster, fire_invocation, turnManager);
    }
}
