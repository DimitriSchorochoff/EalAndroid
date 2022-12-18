package com.example.eal.Class;

import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Database.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Squad {
    public static final String GENERIC_SQUAD_NAME = "Squad";
    //Database attribute
    private String IDSquad;
    private String Name;
    private ArrayList<Ealard> members;
    private GameMode gameMode;

    //Class specific attribute
    private static HashMap<String, String> bufferIDSquadByGameMode = new HashMap<>();

    public Squad(String Name, GameMode gameMode){
        this.IDSquad = UUID.randomUUID().toString();
        this.Name = Name;
        this.members = new ArrayList<>();
        this.gameMode = gameMode;
    }

    public Squad(String IDSquad, String Name, ArrayList<Ealard> members, GameMode gameMode){
        this.IDSquad = IDSquad;
        this.Name = Name;
        this.members = members;
        this.gameMode = gameMode;
    }

    public Squad(DBManager dbManager, String IDSquad){
        //Return a Squad from the database

        Squad t = dbManager.getSquad(IDSquad);

        this.IDSquad = IDSquad;
        this.Name = t.getName();
        this.members = t.getMembers();
        this.gameMode = t.getGameMode();
    }

    public static void setBufferIDSquadByGameMode(GameMode gameMode, String bufferIDSquad) {
        Squad.bufferIDSquadByGameMode.put(gameMode.getName(), bufferIDSquad);
    }

    public static String getBufferIDSquadByGameMode(GameMode gameMode) {
        return bufferIDSquadByGameMode.getOrDefault(gameMode.getName(), null);
    }

    public String getIDSquad() {
        return IDSquad;
    }

    public String getName() {
        return Name;
    }

    public ArrayList<Ealard> getMembers() {
        return members;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setName(String name) {
        Name = name;
    }
}