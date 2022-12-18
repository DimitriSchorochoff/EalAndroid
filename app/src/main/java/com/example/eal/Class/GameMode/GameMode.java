package com.example.eal.Class.GameMode;

import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.GameMode.GameModeDeathMatch.ClassicGameMode;
import com.example.eal.Class.GameMode.GameModeDeathMatch.HeroGameMode;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.Class.Squad;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameMode implements Serializable {
    protected int numberPlayer;
    protected int numberPlayerMin;
    protected int numberPlayerMax;

    protected int numberEalardPerSquad;
    protected int numberEssencePerEalard;
    protected int numberRoadTo;

    protected String name;
    protected String mapRestrictionCriterion;
    protected String squadRestrictionCriterion;
    protected String ealardRestrictionCriterion;

    public GameMode(int numberPlayer, int numberPlayerMin, int numberPlayerMax, int numberEalardPerSquad, int numberEssencePerEalard, int numberRoadTo, String name, String mapRestrictionCriterion, String squadRestrictionCriterion, String ealardRestrictionCriterion){
        this.numberPlayer = numberPlayer;
        this.numberPlayerMin = numberPlayerMin;
        this.numberPlayerMax = numberPlayerMax;
        this.numberEalardPerSquad = numberEalardPerSquad;
        this.numberEssencePerEalard = numberEssencePerEalard;
        this.numberRoadTo = numberRoadTo;
        this.name = name;
        this.mapRestrictionCriterion = mapRestrictionCriterion;
        this.squadRestrictionCriterion = squadRestrictionCriterion;
        this.ealardRestrictionCriterion = ealardRestrictionCriterion;
    }


    public static ArrayList<String> getListAllModeName(){
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add(ClassicGameMode.NAME);
        returnList.add(HeroGameMode.NAME);
        return returnList;
    }

    public static GameMode getGameModeByName(String gameModeName){
        if(gameModeName.equals(ClassicGameMode.NAME)) return new ClassicGameMode(0,0);
        if(gameModeName.equals(HeroGameMode.NAME)) return new HeroGameMode(0,0);
        else return  null;
    }

    public abstract boolean hasPlayerLost(Player player);

    public abstract boolean hasPlayerWon(Player player);

    public int getNumberPlayer() {
        return numberPlayer;
    }

    public int getNumberPlayerMin() {
        return numberPlayerMin;
    }

    public int getNumberPlayerMax() {
        return numberPlayerMax;
    }

    public int getNumberEalardPerSquad() {
        return numberEalardPerSquad;
    }

    public int getNumberEssencePerEalard() {
        return numberEssencePerEalard;
    }

    public int getNumberRoadTo() {
        return numberRoadTo;
    }

    public String getName() {
        return name;
    }

    public String getMapRestrictionCriterion() {
        return mapRestrictionCriterion;
    }

    public String getSquadRestrictionCriterion() {
        return squadRestrictionCriterion;
    }

    public String getEalardRestrictionCriterion() {
        return ealardRestrictionCriterion;
    }

    public void setNumberRoadTo(int numberRoadTo) {
        this.numberRoadTo = numberRoadTo;
    }

    public void setNumberPlayer(int numberPlayer) {
        this.numberPlayer = numberPlayer;
    }

    public void setMapRestrictionCriterion(String mapRestrictionCriterion) {
        this.mapRestrictionCriterion = mapRestrictionCriterion;
    }

    public void setEalardRestrictionCriterion(String ealardRestrictionCriterion) {
        this.ealardRestrictionCriterion = ealardRestrictionCriterion;
    }

    public void setSquadRestrictionCriterion(String squadRestrictionCriterion) {
        this.squadRestrictionCriterion = squadRestrictionCriterion;
    }

    public boolean isMapValid(Map map){
        return map.getMinNumberPlayer() <= getNumberPlayer() && getNumberPlayer() <= map.getMaxNumberPlayer() &&
                map.getNumberEalard() <= getNumberEalardPerSquad() &&
                isMapValidAdditionalCriterion(map);
    }

    protected abstract boolean isMapValidAdditionalCriterion(Map map);

    public boolean isSquadValid(Squad squad){
        for(Ealard e: squad.getMembers()){
            if (!isEalardValid(e))
                return false;
        }

        return squad.getMembers().size() == numberEalardPerSquad &&
                isSquadAdditionalCriterion(squad);
    }

    protected abstract boolean isSquadAdditionalCriterion(Squad squad);

    public boolean isEalardValid(Ealard ealard){
        return ealard.getEssence() >= 0 &&
                isEalardValidAdditionalCriterion(ealard);
    }

    protected abstract boolean isEalardValidAdditionalCriterion(Ealard ealard);
}
