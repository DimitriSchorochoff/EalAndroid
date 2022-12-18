package com.example.eal.Class.InGame;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.Entities.Entity;
import com.example.eal.Class.Entities.Invocation.Ethered.Runic_totem_ethered;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.InGame.InGameInvocation.InGameFireInvocation.InGameFireInvocation;
import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InGameSquad implements Serializable {
    private ArrayList<InGameEntity> inGameEntities;
    private boolean onTurn;
    private TurnManager turnManager;
    private Player player;

    public InGameSquad(TurnManager turnManager, Player player){
        this.inGameEntities = new ArrayList<>();

        this.onTurn = false;
        this.turnManager = turnManager;
        this.player = player;
    }

    public InGameSquad(ArrayList<Ealard> ealards, Player player, TurnManager turnManager){
        this.inGameEntities = new ArrayList<>();
        for (Ealard e: ealards)
            inGameEntities.add(new InGameEalard(e, this));

        this.onTurn = false;
        this.turnManager = turnManager;
        this.player = player;
    }

    public static InGameSquad getPuppetInGameSquad(Map map, GameMode gameMode, TurnManager turnManager, Player player){
        InGameSquad returnSquad = new InGameSquad(turnManager,player);

        InGameEalard puppet;
        String puppetName;
        for(int i = 1; i<=map.getNumberEalard(); i++){
            puppetName = String.format("%s_%d", Ealard.GENERIC_EALARD_NAME, i);
            puppet = new InGameEalard(Ealard.puppetEalard(puppetName, gameMode.getNumberEssencePerEalard()), returnSquad);
            returnSquad.addInGameEntity(puppet);
        }

        return returnSquad;
    }

    public ArrayList<String> getUsedNames(){
        ArrayList<String> returnList = new ArrayList<>();

        for(InGameEntity i: inGameEntities)
            returnList.add(i.getName());

        return returnList;
    }

    public boolean hasActiveEntity(){
        for (InGameEntity e: inGameEntities)
            if(e.isOnTurn())
                return true;

        return false;
    }

    public boolean isOnTurn() {
        return onTurn;
    }

    public ArrayList<InGameEntity> getInGameEntities() {
        return (ArrayList<InGameEntity>) inGameEntities.clone();
    }

    public ArrayList<InGameEntity> getInGameEalards(){
        Predicate<InGameEntity> isEalard = inGameEntity -> inGameEntity.getEntityType() == Entity.EntityType.EALARD;

        return getInGameEntities().stream()
                .filter(isEalard)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<InGameEntity> getInGameEntitiesFiltered(Predicate<InGameEntity> filter){
        if(filter == null) return getInGameEntities();
        return inGameEntities.stream()
                .filter(filter)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<InGameEntity> getSpellTargets(){
        return getInGameEntities().stream()
                .filter(InGameEntity::isSpellTarget)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addInGameEntity(InGameEntity inGameEntity){
        inGameEntities.add(inGameEntity);
    }

    public void removeInGameEntity(InGameEntity inGameEntity){
        inGameEntities.remove(inGameEntity);
    }

    public boolean containsInGameEntity(InGameEntity inGameEntity){
        return inGameEntities.contains(inGameEntity);
    }

    public ArrayList<InGameEntity> getInGameInvocations(){
        Predicate<InGameEntity> isInvocation = inGameEntity -> {
            switch (inGameEntity.getEntityType()){
                case GLYPHE:
                case ETHERED:
                    return true;

                default:
                    return false;
            }
        };

        return getInGameEntities().stream()
                .filter(isInvocation)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<InGameEntity> getInGameFireInvocation(){
        return getInGameEntities().stream()
                .filter((i)-> i instanceof InGameFireInvocation)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean containsRunicTotemEthered(){
        for(InGameEntity inGameEntity: inGameEntities){
            if(inGameEntity instanceof InGameInvocation && ((InGameInvocation) inGameEntity).getInvocation() instanceof Runic_totem_ethered)
                return true;
        }

        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPuppetSquad() {
        return player.isPuppetPlayer();
    }

    public boolean isDoneForRound(){
        for(InGameEntity inGameEntity: inGameEntities){
            if(!inGameEntity.isDoneForRound())
                return false;
        }
        return true;
    }
    public void startTurn(){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("Start turn of %s's squad", getPlayer().getName()), Map.SQUAD_INDENTATION);

        onTurn = true;
    }

    public void endTurn(){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("End turn of %s's squad", getPlayer().getName()), Map.SQUAD_INDENTATION);

        onTurn = false;
        turnManager.endTurnSquad();
    }

    public void restore(){
        for(InGameEntity inGameEntity: inGameEntities)
            inGameEntity.restore();
    }
}