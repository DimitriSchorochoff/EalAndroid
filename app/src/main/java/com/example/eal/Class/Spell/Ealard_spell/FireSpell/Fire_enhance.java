package com.example.eal.Class.Spell.Ealard_spell.FireSpell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameFireInvocation.InGameFireInvocation;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Area;
import com.example.eal.Class.Spell.Ealard_spell.Ealard_spell;
import com.example.eal.Class.Spell.Effect;
import com.example.eal.Class.Spell.Element.Fire;
import com.example.eal.Class.Spell.Spell;

import java.util.ArrayList;

public class Fire_enhance extends Spell  implements Ealard_spell {
    private static final int mobility_boost = 2;
    private static final int power_boost = 3;

    private static final String SPELL_NAME = "Fire enhance";

    private static ArrayList<InGameFireInvocation> enhancedInvocation = new ArrayList<>();

    public Fire_enhance() {
        super(new Fire(),
                0,
                Effect.OTHER,
                3,
                new Area(0,0, Area.AreaType.CLASSIC),
                new Area(0,0, Area.AreaType.ALL),
                null,
                String.format("Boost every fire entities. They gain %d mobility and %d power.", getMobility_boost(), getPower_boost()),
                Spell.MASTERY_VALUE);
    }

    @Override
    public void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        //Since we enhance all fire invocation, we can clear the list each time we cast the spell.
        enhancedInvocation = new ArrayList<>();
        for(Player p: players){
            InGameFireInvocation fire_invocation;
            for(InGameEntity i: p.getOwnedInGameSquad().getInGameFireInvocation()){
                fire_invocation = (InGameFireInvocation) i;
                ((InGameFireInvocation) i).on_enhance();
                enhancedInvocation.add(fire_invocation);
            }
        }
    }

    @Override
    public String getSpellName() {
        return SPELL_NAME;
    }

    public static String getSPELLNAME(){
        return SPELL_NAME;
    }

    public static ArrayList<InGameFireInvocation> getEnhancedInvocation() {
        return enhancedInvocation;
    }

    public static int getMobility_boost() {
        return mobility_boost;
    }

    public static int getPower_boost() {
        return power_boost;
    }
}
