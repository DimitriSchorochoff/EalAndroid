package com.example.eal.Class.Map;

import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.Map.BattleRoyal.DesertMap;
import com.example.eal.Class.Map.Duel.AuroraBand;
import com.example.eal.Class.Map.Duel.BridgeOfDestiny;
import com.example.eal.Class.Map.Duel.LavalandMap;
import com.example.eal.Class.Map.Duel.MountainBattlefieldMap;
import com.example.eal.Class.Map.Duel.TempleMap;
import com.example.eal.Class.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Map {
    public static int GAME_INDENTATION = 0;
    public static int ROUND_INDENTATION = 2;
    public static int SQUAD_INDENTATION = 4;
    public static int ENTITY_INDENTATION = 6;
    public static int SPELL_AND_ACTION_INDENTATION = 8;
    public static int EFFECT_INDENTATION = 10;

    //TODO MAP SORTER BY NAME, BY NUMBER PLAYER, NUMBER EALARD. BY TYPE ?

    public static ArrayList<Map> getListAllMaps(){
        ArrayList<Map> returnList = new ArrayList();

        //Duel maps
        returnList.add(new AuroraBand());
        returnList.add(new BridgeOfDestiny());
        returnList.add(new LavalandMap());
        returnList.add(new MountainBattlefieldMap());
        returnList.add(new TempleMap());

        //BattleRoyal maps
        returnList.add(new DesertMap());

        return returnList;
    }

    public static ArrayList<Map> getListMapsByGameMode(GameMode gameMode){
        return filterMapsByGameMode(getListAllMaps(), gameMode);
    }

    private static ArrayList<Map> filterMapsByGameMode(ArrayList<Map> maps, GameMode gameMode){
        Predicate<Map> byPlayer = gameMode::isMapValid;

        return maps.stream()
                .filter(byPlayer)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static HashMap<Player, Float> getPlayerByWin(ArrayList<Player> players, ArrayList<Map> maps){
        //players should at least contain one player

        HashMap<Player, Float> playerByWin = new HashMap<>();
        for(Player p: players)
            playerByWin.put(p, (float) 0);

        for(Map m: maps){
            if(m.getWinner() != null)
                playerByWin.put(m.getWinner(), playerByWin.get(m.getWinner()) + 1);
            else
                for(Player p: players)
                    playerByWin.put(p, (float) (playerByWin.get(p) + 0.5));
        }

        return playerByWin;
    }

    public static Player getLastPlayerWithLeastWin(ArrayList<Player> players, ArrayList<Map> maps){
        HashMap<Player, Float> playerByWin = getPlayerByWin(players, maps);

        Player returnPlayer = players.get(0);
        //We use <= to get last player in case of ex aequo
        for(Player p: players){
            if(playerByWin.get(p) <= playerByWin.get(returnPlayer))
                returnPlayer = p;
        }

        return returnPlayer;
    }

    public static Player getFirstPlayerWithLeastWin(ArrayList<Player> players, ArrayList<Map> maps){
        HashMap<Player, Float> playerByWin = getPlayerByWin(players, maps);

        Player returnPlayer = players.get(0);
        //We use < to get first player in case of ex aequo
        for(Player p: players){
            if(playerByWin.get(p) < playerByWin.get(returnPlayer))
                returnPlayer = p;
        }

        return returnPlayer;
    }

    public static float getMostWin(ArrayList<Player> players, ArrayList<Map> maps){
        HashMap<Player, Float> playerByWin = getPlayerByWin(players, maps);

        float mostWin = 0;
        for(Player p: playerByWin.keySet()){
            mostWin = Math.max(mostWin, playerByWin.get(p));
        }

        return mostWin;
    }


    private String name;
    private int minNumberPlayer;
    private int maxNumberPlayer;
    private int numberEalard;
    private Player winner;
    private StringBuilder match_historic;

    public Map(String name, int minNumberPlayer, int maxNumberPlayer, int numberEalard){
        this.name = name;
        this.minNumberPlayer = minNumberPlayer;
        this.maxNumberPlayer = maxNumberPlayer;
        this.numberEalard = numberEalard;
        this.winner = null;
        this.match_historic = new StringBuilder();
    }

    public String getName() {
        return name;
    }

    public int getNumberEalard() {
        return numberEalard;
    }

    public int getMinNumberPlayer() {
        return minNumberPlayer;
    }

    public int getMaxNumberPlayer() {
        return maxNumberPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void writeLineInHistoric(String line, int indentation){
        String indentationString = new String(new char[indentation]).replace("\0", " ");
        match_historic.append(String.format("%s• %s\n", indentationString, line));
    }

    public String getMatchHistoric(){
        return match_historic.toString();
    }
}
