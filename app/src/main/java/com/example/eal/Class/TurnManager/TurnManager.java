package com.example.eal.Class.TurnManager;


import android.content.Intent;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Activity.Play.PlayTurnTransitionActivity;
import com.example.eal.Class.TurnManager.CountdownWolfAction.CountdownWolfAction;
import com.example.eal.Application.App;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TurnManager {
    private ArrayList<CountdownAction> countdownActions;
    private ArrayList<Player> players;

    private int roundNumber;
    private int numberPlayers;
    private int startRoundPlayerPosition;
    private int activePlayerPosition;

    private boolean isConsumerRunning;

    public TurnManager(ArrayList<Player> players){
        this.countdownActions = new ArrayList<>();
        this.players = players;

        this.roundNumber = 1;
        this.numberPlayers = -1;
        this.startRoundPlayerPosition = 0;
        this.isConsumerRunning = false;
    }


    public void addCountdownAction(CountdownAction countdownAction){
        countdownActions.add(countdownAction);
    }

    private boolean callCountDownAction(int position){
        CountdownAction firstCountAction = countdownActions.get(position);
        if(firstCountAction.getCountdown() <= 1){
            firstCountAction.execute();
            PlayInGameActivity.checkEndGame();
            return true;
        } else{
            firstCountAction.decCountDown();
            PlayInGameActivity.checkEndGame();
            return false;
        }
    }

    private void callAllCountDownAction(){
        isConsumerRunning = true;

        PlayInGameActivity.getLastMap().writeLineInHistoric("End round actions are called", Map.ROUND_INDENTATION);

        ArrayList<CountdownAction> actionConsumed = new ArrayList<>();
        int sizeBeforeConsumption = countdownActions.size();

        for(int i = 0; i < sizeBeforeConsumption; i++){
            if(callCountDownAction(i))
                actionConsumed.add(countdownActions.get(i));
        }

        countdownActions.removeAll(actionConsumed);

        isConsumerRunning = false;
    }

    public void startTurnSquad(){
        Player p;
        for(int i = 0; i< numberPlayers; i++){
            p = players.get(activePlayerPosition);
            if(!p.hasLost() && !p.getOwnedInGameSquad().isDoneForRound()){
                p.getOwnedInGameSquad().startTurn();
                return;
            } else {
                activePlayerPositionInc();
            }
        }

        //If no player can start turn the round is over
        endRound();
    }

    public void endTurnSquad(){
        activePlayerPositionInc();

        PlayInGameActivity.checkEndGame();

        //Call transition screen if needed
        if(!PlayInGameActivity.getGameEnded()){
            Intent intent = new Intent(App.getContext(), PlayTurnTransitionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);
        }

        startTurnSquad();
    }

    public void startRound(){
        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("Start round %d: everyone is restored", roundNumber), Map.ROUND_INDENTATION);

        restoreAll();
        activePlayerPosition = startRoundPlayerPosition;
        startTurnSquad();
    }

    public void endRound(){
        callAllCountDownAction();

        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("End round %d", roundNumber), Map.ROUND_INDENTATION);

        startRoundPlayerPositionInc();

        roundNumber++;
        startRound();
    }

    public void startGame(){
        reset();

        PlayInGameActivity.getLastMap().writeLineInHistoric("Game begin", Map.GAME_INDENTATION);
        startRoundPlayerPosition = PlayInGameActivity.getPositionFirstPlayerWithLeastWin();
        PlayInGameActivity.setGameEnded(false);
        startRound();
    }

    private void restoreAll(){
        for(Player p:players)
            p.getOwnedInGameSquad().restore();
    }

    private void reset(){
        for(Player p: players)
            p.resetSurrender();
        this.numberPlayers = players.size();
        this.countdownActions = new ArrayList<>();
    }

    public void startRoundPlayerPositionInc(){
        startRoundPlayerPosition = (startRoundPlayerPosition+1) % numberPlayers;
    }

    public void activePlayerPositionInc(){
        activePlayerPosition = (activePlayerPosition+1) % numberPlayers;
    }

    public int getActivePlayerPosition() {
        return activePlayerPosition;
    }

    public int getStartRoundPlayerPosition() {
        return startRoundPlayerPosition;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public ArrayList<CountdownAction> getCountdownActions() {
        return countdownActions;
    }

    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    public boolean containsClearBloodSacrificeAction(){
        for(CountdownAction c: countdownActions)
            if(c instanceof CountdownClearBloodSacrifice)
                return true;

        return false;
    }

    public ArrayList<CountdownAction> getWolfCountdownActions(){
        Predicate<CountdownAction> isWolfAction = (c)-> c instanceof CountdownWolfAction;

        return (ArrayList<CountdownAction>) getCountdownActions().stream()
                .filter(isWolfAction)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void removeTargetedAction(InGameEntity target){
        if(isConsumerRunning()) return;

        ArrayList<CountdownAction> actionsToRemove = countdownActions
                .stream()
                .filter((a)-> a instanceof CountdownActionTargeted && ((CountdownActionTargeted) a).getTarget() == target)
                .collect(Collectors.toCollection(ArrayList::new));

        countdownActions.removeAll(actionsToRemove);
    }

    public boolean isConsumerRunning() {
        return isConsumerRunning;
    }
}
