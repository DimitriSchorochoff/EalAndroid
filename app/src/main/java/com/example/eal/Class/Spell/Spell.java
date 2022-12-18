package com.example.eal.Class.Spell;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Activity.Play.PlayEntityActivity;
import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Activity.Play.PlayPuppetEalardActivity;
import com.example.eal.Class.Spell.Ealard_spell.BloodSpell.ScratchMove;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.RuneOfWindPillar;
import com.example.eal.Class.Spell.Element.Blood;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Activity.SpellActivity;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_ape;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_bear;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_eagle;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_elephant;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_enhance;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_octopus;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_rhinoceros;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_wolf;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.AerialBoots;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.AerialProtection;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.RuneOfAttirance;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.RuneOfDepressionCorridor;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.RuneOfDisplacement;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.RuneOfExplosion;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.RuneOfProjection;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.RuneOfPropagation;
import com.example.eal.Class.Spell.Ealard_spell.WindSpell.Runic_totem;
import com.example.eal.Class.Spell.Element.Darkness;
import com.example.eal.Class.Spell.Element.Element;
import com.example.eal.Class.Spell.Element.Fire;
import com.example.eal.Class.Spell.Element.Wind;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_lion;
import com.example.eal.Class.Spell.Ealard_spell.FireSpell.Fire_phoenix;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_ape_juggle;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_bear_slash;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_eagle_strike;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_elephant_trample;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_lion_roar;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_octopus_juggle;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_octopus_slam;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_phoenix_wing_slash;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_rhinoceros_charge;
import com.example.eal.Class.Spell.Invocation_spell.FireInvocationSpell.Fire_wolf_bite;
import com.example.eal.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Spell{
    public static final int MASTERY_VALUE = 5;

    public static ArrayList<String> getListAllElement(){
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add(Fire.getELEMENT());
        returnList.add(Wind.getELEMENT());
        returnList.add(Darkness.getELEMENT());

        return returnList;
    }

    public static int getElementColor(String element){
        if(element.equals(Fire.getELEMENT())) return App.getContext().getResources().getColor(R.color.fire);
        else if(element.equals(Wind.getELEMENT())) return App.getContext().getResources().getColor(R.color.wind);
        else if(element.equals(Darkness.getELEMENT())) return App.getContext().getResources().getColor(R.color.darkness);
        else if(element.equals(Blood.getELEMENT())) return App.getContext().getResources().getColor(R.color.blood);
        else return -1;
    }

    public static String getEffectName(Effect effect){
        switch (effect){
            case DAMAGE:
                return "Damage";
            case SHIELD:
                return "Shield";
            case GLYPHE:
                return "Glyphe";
            case ETHERED:
                return "Ethered";
            case CREATURE:
                return "Creature";
            case ENERGY_BUFF:
                return "Energy buff";
            case MOBILITY_BUFF:
                return "Mobility buff";
            case ENERGY_LOSS:
                return "Energy loss";
            case MOBILITY_LOSS:
                return "Mobility loss";
            case DISPLACEMENT:
                return "Displacement";
            case OTHER:
                return "Other";
        }
        return null;
    }

    public static Drawable getEffectImage(Effect effect){
        switch (effect){
            case DAMAGE:
                return App.getContext().getResources().getDrawable(R.drawable.ic_damage_icon);
            case ETHERED:
                return App.getContext().getResources().getDrawable(R.drawable.ic_summon_icon);
        }
        return null;
    }

    public static ArrayList<String> getListAllInvocationSpell(){
        ArrayList<String> returnList = new ArrayList<>();

        return returnList;
    }

    public static ArrayList<String> getListAllEalardSpell(){
        ArrayList<String> returnList = new ArrayList<>();
        //Fire
        returnList.add(Fire_lion.getSPELLNAME());
        returnList.add(Fire_phoenix.getSPELLNAME());
        returnList.add(Fire_eagle.get_SPELL_NAME());
        returnList.add(Fire_rhinoceros.get_SPELL_NAME());
        //returnList.add(Fire_wolf.getSPELLNAME());
        returnList.add(Fire_bear.get_SPELL_NAME());
        returnList.add(Fire_elephant.get_SPELL_NAME());
        returnList.add(Fire_octopus.getSPELLNAME());
        returnList.add(Fire_ape.getSPELLNAME());
        returnList.add(Fire_enhance.getSPELLNAME());

        //Wind
        returnList.add(AerialBoots.getSPELLNAME());
        returnList.add(AerialProtection.getSPELLNAME());
        returnList.add(RuneOfAttirance.getSPELLNAME());
        returnList.add(RuneOfExplosion.getSPELLNAME());
        returnList.add(RuneOfProjection.getSPELLNAME());
        returnList.add(RuneOfPropagation.getSPELLNAME());
        returnList.add(RuneOfDisplacement.getSPELLNAME());
        returnList.add(RuneOfDepressionCorridor.getSPELLNAME());
        returnList.add(RuneOfWindPillar.getSPELLNAME());
        returnList.add(Runic_totem.getSPELLNAME());

        //Blood
        returnList.add(ScratchMove.getSPELLNAME());

        return returnList;
    }

    public static Spell getSpell(String spell){
        //No switch cause it don't allow variable as case statement

        // Ealard spell
        //Fire
        if(spell.equals(Fire_lion.getSPELLNAME())) return new Fire_lion();
        if(spell.equals(Fire_phoenix.getSPELLNAME())) return new Fire_phoenix();
        if(spell.equals(Fire_enhance.getSPELLNAME())) return new Fire_enhance();
        if(spell.equals(Fire_eagle.get_SPELL_NAME())) return new Fire_eagle();
        if(spell.equals(Fire_rhinoceros.get_SPELL_NAME())) return new Fire_rhinoceros();
        if(spell.equals(Fire_wolf.getSPELLNAME())) return new Fire_wolf();
        if(spell.equals(Fire_bear.get_SPELL_NAME())) return new Fire_bear();
        if(spell.equals(Fire_elephant.get_SPELL_NAME())) return new Fire_elephant();
        if(spell.equals(Fire_octopus.getSPELLNAME())) return new Fire_octopus();
        if(spell.equals(Fire_ape.getSPELLNAME())) return new Fire_ape();

        //Wind
        if(spell.equals(AerialBoots.getSPELLNAME())) return new AerialBoots();
        if(spell.equals(AerialProtection.getSPELLNAME())) return new AerialProtection();
        if(spell.equals(RuneOfAttirance.getSPELLNAME())) return new RuneOfAttirance();
        if(spell.equals(RuneOfExplosion.getSPELLNAME())) return new RuneOfExplosion();
        if(spell.equals(RuneOfProjection.getSPELLNAME())) return new RuneOfProjection();
        if(spell.equals(RuneOfPropagation.getSPELLNAME())) return new RuneOfPropagation();
        if(spell.equals(RuneOfDisplacement.getSPELLNAME())) return new RuneOfDisplacement();
        if(spell.equals(RuneOfDepressionCorridor.getSPELLNAME())) return new RuneOfDepressionCorridor();
        if(spell.equals(RuneOfWindPillar.getSPELLNAME())) return new RuneOfWindPillar();
        if(spell.equals(Runic_totem.getSPELLNAME())) return new Runic_totem();

        //Blood
        if(spell.equals(ScratchMove.getSPELLNAME())) return new ScratchMove();

        // Invocation spell
        //Fire
        if(spell.equals(Fire_lion_roar.getSPELLNAME())) return new Fire_lion_roar();
        if(spell.equals(Fire_eagle_strike.getSPELLNAME())) return new Fire_eagle_strike();
        if(spell.equals(Fire_rhinoceros_charge.getSPELLNAME())) return new Fire_rhinoceros_charge();
        if(spell.equals(Fire_phoenix_wing_slash.getSPELLNAME())) return new Fire_phoenix_wing_slash();
        if(spell.equals(Fire_wolf_bite.getSPELLNAME())) return new Fire_wolf_bite();
        if(spell.equals(Fire_bear_slash.getSPELLNAME())) return new Fire_bear_slash();
        if(spell.equals(Fire_elephant_trample.getSPELLNAME())) return new Fire_elephant_trample();
        if(spell.equals(Fire_octopus_slam.getSPELLNAME())) return new Fire_octopus_slam();
        if(spell.equals(Fire_octopus_juggle.getSPELLNAME())) return new Fire_octopus_juggle();
        if(spell.equals(Fire_ape_juggle.getSPELLNAME())) return new Fire_ape_juggle();

        else return null;
    }

    public static ArrayList<String> filterByElement(ArrayList<String> spellsToFilter, Element filterElement){
        Predicate<String> matchElement = (s)->{
            Spell spell = getSpell(s);
            return spell.getElement().equals(filterElement);
        };

        return spellsToFilter.stream()
                .filter(matchElement)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<String> filterByMastery(ArrayList<String> spellsToFilter, ArrayList<String> currentSpells){
        HashMap<String, Integer> elementUse = elementByNumberSpell(currentSpells);

        Predicate<String> isMasteryReach = (s)->{
            Spell spell = getSpell(s);
            return elementUse.getOrDefault(spell.getElementName(), 0) >= spell.getMasteryLevel();
        };

        return spellsToFilter.stream()
                .filter(isMasteryReach)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static int getHighestMasteryLevel(ArrayList<String> spells){
        int max = 0;
        Spell currentSpell;
        for(String spellName: spells){
            currentSpell = Spell.getSpell(spellName);
            max = Math.max(max, currentSpell.getMasteryLevel());
        }

        return max;
    }

    public static ArrayList<String> filterByRemovingHighestMasteryLevel(ArrayList<String> spellsToFilter){
        int highestMasteryLevel = getHighestMasteryLevel(spellsToFilter);

        return spellsToFilter.stream()
                .filter((s)->(Spell.getSpell(s)).getMasteryLevel() < highestMasteryLevel)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<String> getSpellListElements(ArrayList<String> spells){
        //Each spell in spells MUST be a valid spellname

        ArrayList<String> returnList = new ArrayList<>();
        String element;
        for (String s: spells){
            element = getSpell(s).getElementName();
            if (! returnList.contains(element))
                returnList.add(element);
        }
        Collections.sort(returnList);
        return returnList;
    }

    public static HashMap<String, Integer> elementByNumberSpell(ArrayList<String> spells){
        HashMap<String, Integer> elementUse = new HashMap<>();
        String element;
        for (String s: spells){
            element = getSpell(s).getElementName();
            elementUse.put(element, elementUse.getOrDefault(element, 0)+1);
        }

        return elementUse;
    }

    public static ArrayList<String> getBestSpellListElements(ArrayList<String> spells){
        //Each spell in spells MUST be a valid spellname
        if(spells.isEmpty()) return new ArrayList<>();

        //Map the number of each type's usage
        HashMap<String, Integer> elementUse = elementByNumberSpell(spells);

        //Get the max usage then filter to keeps only element with max usage
        int max = Collections.max(elementUse.values());
        ArrayList<String> returnList = new ArrayList<>();

        for (String key: elementUse.keySet()){
            if (elementUse.get(key) == max)
                returnList.add(key);
        }

        Collections.sort(returnList);
        return returnList;
    }

    public static InGameInvocation summonInvocation(InGameEntity caster, Invocation invocation){
        InGameSquad squad = caster.getInGameSquad();
        InGameInvocation returnInvocation = new InGameInvocation(invocation, caster);
        squad.addInGameEntity(returnInvocation);

        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s has been summoned", returnInvocation.getName()), Map.EFFECT_INDENTATION);

        return returnInvocation;
    }

    protected InGameEntity owner;

    protected Element element;
    protected int power;
    protected Effect effect;
    protected int energy_cost;
    protected Area range;
    protected Area area;
    protected Invocation invocation; //null if no invocation related
    protected String description;
    protected int masteryLevel;
    protected boolean isMultiHit;

    public Spell(Element element, int power, Effect effect, int energy_cost, Area range, Area area, Invocation invocation, String description, int masteryLevel){
        this.element = element;
        this.power = power;
        this.effect = effect;
        this.energy_cost = energy_cost;
        this.range = range;
        this.area = area;
        this.invocation = invocation;
        this.description = description;
        this.masteryLevel = masteryLevel;
        this.isMultiHit = false;
        this.owner = null;

        if(effect == Effect.GLYPHE ||
        effect == Effect.ETHERED){
            if(invocation == null)
                Log.d("Error", "Invocation spell must have an invocation");
        }
    }

    public abstract String getSpellName();

    public void cast(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s have been casted", getSpellName()), Map.SPELL_AND_ACTION_INDENTATION);

        caster.updateSpellListShown(getSpellName());
        caster.getAlreadyCastedSpell().add(getSpellName());

        caster.spellUseEnergy(getEnergy_cost());

        cast_effect(activity, caster, players, turnManager);
    }

    public void extra_cast(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s have been extra casted", getSpellName()), Map.SPELL_AND_ACTION_INDENTATION);

        cast_effect(activity, caster, players, turnManager);
    }

    private void cast_effect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager){
        spellEffect(activity, caster, players, turnManager);

        if(activity instanceof PlayEntityActivity)
            ((PlayEntityActivity) activity).majValue();
        else if(activity instanceof PlayPuppetEalardActivity)
            ((PlayPuppetEalardActivity) activity).majValue();
        else if (activity instanceof SpellActivity)
            ((SpellActivity) activity).afterSpellCast();

        String castString= String.format("%s have been casted", getSpellName());
        Toast.makeText(activity, castString, Toast.LENGTH_SHORT).show();

        PlayInGameActivity.checkEndGame();
    }

    public abstract void spellEffect(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager);

    public String getElementName() {
        return element.getElementName();
    }

    public Area getRange() {
        return range;
    }

    public Area getArea() {
        return area;
    }

    public Effect getEffect() {
        return effect;
    }

    public Element getElement() {
        return element;
    }

    public int getEnergy_cost() {
        return energy_cost;
    }

    public int getPower() {
        return power;
    }

    public Invocation getInvocation() {
        return invocation;
    }

    public String getDescription() {
        return description;
    }

    public int getMasteryLevel() {
        return masteryLevel;
    }

    public void setOwner(InGameEntity owner) {
        this.owner = owner;
    }

    public InGameEntity getOwner() {
        return owner;
    }

    public boolean isMultiHit() {
        return isMultiHit;
    }

    public void setMultiHit(boolean multiHit) {
        isMultiHit = multiHit;
    }
}

