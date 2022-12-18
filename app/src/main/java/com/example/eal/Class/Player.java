package com.example.eal.Class;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Map.Map;

import java.io.Serializable;

public class Player implements Serializable {
    public static final String GENERIC_PLAYER_NAME = "Player";
    public static final String GENERIC_PUPPET_NAME = "Puppet";

    private String name;
    private String ownedSquadID;
    private InGameSquad ownedInGameSquad;
    private boolean isPuppetPlayer;
    private GameMode gameMode;
    private boolean showInformationDialog;
    private boolean hasSurrendered;

    public Player(String name, boolean isPuppetPlayer, GameMode gameMode){
        this.name = name;
        this.ownedSquadID = null;
        this.ownedInGameSquad = null;
        this.isPuppetPlayer = isPuppetPlayer;
        this.gameMode = gameMode;
        this.showInformationDialog = true;
        this.hasSurrendered = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnedSquadID(String ownedSquadID) {
        this.ownedSquadID = ownedSquadID;
    }

    public void setOwnedInGameSquad(InGameSquad ownedInGameSquad) {
        this.ownedInGameSquad = ownedInGameSquad;
    }

    public String getName() {
        return name;
    }

    public InGameSquad getOwnedInGameSquad() {
        return ownedInGameSquad;
    }

    public String getOwnedSquadID() {
        return ownedSquadID;
    }

    public boolean isPuppetPlayer() {
        return isPuppetPlayer;
    }

    public boolean hasLost(){
        return gameMode.hasPlayerLost(this) || hasSurrendered();
    }

    public boolean hasWon(){
        return gameMode.hasPlayerWon(this);
    }

    public void setShowInformationDialog(boolean showInformationDialog) {
        this.showInformationDialog = showInformationDialog;
    }

    public boolean isShowInformationDialog() {
        return showInformationDialog;
    }

    public void surrender(){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s surrendered", getName()), Map.GAME_INDENTATION);
        setHasSurrendered(true);

        if(!PlayInGameActivity.checkEndGame()){
            InGameEntity activeEntity = PlayInGameActivity.getActiveInGameEntity();
            if(activeEntity != null)
                activeEntity.endTurn();

            if(getOwnedInGameSquad().isOnTurn())
                getOwnedInGameSquad().endTurn();
        }
    }

    public void resetSurrender(){
        setHasSurrendered(false);
    }

    protected void setHasSurrendered(boolean hasSurrendered) {
        this.hasSurrendered = hasSurrendered;
    }

    public boolean hasSurrendered() {
        return hasSurrendered;
    }
}
