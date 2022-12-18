package com.example.eal.Class.Spell;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Invocation.Invocation;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.Element.Element;
import com.example.eal.Dialog.TargetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public abstract class TargetedSpell extends Spell implements TargetDialog.Listener {
    private AppCompatActivity activity;
    private ArrayList<Player> players;
    private InGameEntity caster;
    private TurnManager turnManager;
    private boolean onExtraCast;

    protected HashMap<InGameEntity, Integer> targets;
    protected Predicate<InGameEntity> targetFilter;

    public TargetedSpell(Element element, int power, Effect effect, int energy_cost, Area range, Area area, Invocation invocation, String description, int masteryLevel) {
        super(element, power, effect, energy_cost, range, area, invocation, description, masteryLevel);

        targetFilter = InGameEntity::isSpellTarget;
    }

    @Override
    public final void cast(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        if(this.activity == null && this.players == null && this.caster == null && this.turnManager == null){
            initAttributeAndShowDialog(activity, caster, players, turnManager);
            onExtraCast = false;
        } else{
            super.cast(activity, caster, players, turnManager);
            clean();
        }
    }

    @Override
    public final void extra_cast(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager) {
        if(this.activity == null && this.players == null && this.caster == null && this.turnManager == null){
            initAttributeAndShowDialog(activity, caster, players, turnManager);
            onExtraCast = true;
        } else{
            super.extra_cast(activity, caster, players, turnManager);
            clean();
        }
    }

    @Override
    public ArrayList<Player> getPlayers(){
        //Can only be accessed after cast() !
        return players;
    }

    @Override
    public InGameEntity getCaster() {
        return caster;
    }

    @Override
    public void onCancel() {
        clean();
    }

    @Override
    public TargetedSpell getTargetedSpell(){
        return this;
    }

    @Override
    public boolean extraCast() {
        return onExtraCast;
    }

    public void setTargets(HashMap<InGameEntity, Integer> targets){
        this.targets = new HashMap<>();
        this.targets.putAll(targets);

        if(onExtraCast)
            extra_cast(activity, caster, players, turnManager);
        else
            cast(activity, caster, players, turnManager);
    }

    public HashMap<InGameEntity, Integer> getTargets() {
        return targets;
    }

    public Predicate<InGameEntity> getTargetFilter() {
        return targetFilter;
    }

    private void initAttributeAndShowDialog(AppCompatActivity activity, InGameEntity caster, ArrayList<Player> players, TurnManager turnManager){
        this.activity = activity;
        this.players = players;
        this.caster = caster;
        this.turnManager = turnManager;
        this.targets = null;


        TargetDialog targetDialog = TargetDialog.newInstance(TargetedSpell.this);
        targetDialog.show(activity.getSupportFragmentManager(), null);
    }

    public void setTargetFilter(Predicate<InGameEntity> targetFilter) {
        this.targetFilter = targetFilter;
    }

    private void clean(){
        //We don't clean targets to keep it's usage for dialog, etc
        activity = null;
        players = null;
        caster = null;
        turnManager = null;
    }

    public static void dealDamageToTargets(int damage, HashMap<InGameEntity, Integer> targets){
        for(InGameEntity i: targets.keySet())
            for(int j = 0; j < targets.get(i); j++)
                i.takeDamage(damage);
    }

    public static void dealDamageAndSideDamageToTargets(int mainDamage, int sideDamage, HashMap<InGameEntity, Integer> targets){
        int sumDamage;
        for(InGameEntity i: targets.keySet()) {
            sumDamage = mainDamage;
            if(targets.get(i) > 1)
                sumDamage += sideDamage * targets.get(i)-1;

            i.takeDamage(sumDamage);
        }
    }

    public static void giveShieldToTargets(int shield, HashMap<InGameEntity, Integer> targets){
        for(InGameEntity i: targets.keySet()){
            for(int j = 0; j < targets.get(i); j++)
                i.gainShield(shield);
        }
    }

    public static void giveEnergyToTargets(int energy, HashMap<InGameEntity, Integer> targets){
        for(InGameEntity i: targets.keySet()){
            for(int j = 0; j < targets.get(i); j++)
                i.gainEnergy(energy);
        }
    }

    public static void giveMobilityToTargets(int mobility, HashMap<InGameEntity, Integer> targets){
        for(InGameEntity i: targets.keySet()){
            for(int j = 0; j < targets.get(i); j++)
                i.gainMobility(mobility);
        }
    }

    public static void loseEnergyToTargets(int energy, HashMap<InGameEntity, Integer> targets){
        for(InGameEntity i: targets.keySet()){
            for(int j = 0; j < targets.get(i); j++)
                i.effectLoseEnergy(energy);
        }
    }

    public static void loseMobilityToTargets(int mobility, HashMap<InGameEntity, Integer> targets){
        for(InGameEntity i: targets.keySet()){
            for(int j = 0; j < targets.get(i); j++)
                i.effectLoseMobility(mobility);
        }
    }

    public String getTargetsNames(){
        StringBuilder returnString = new StringBuilder();
        int size = targets.size();
        int position = 0;

        for(InGameEntity i: targets.keySet()){
            if(position == size-1)
                returnString.append((i.getCompleteName()));
            else if(position == size -2)
                returnString.append(String.format("%s and ",i.getCompleteName()));
            else
                returnString.append(String.format("%s, ", i.getCompleteName()));

            position++;
        }
        return returnString.toString();
    }
}
