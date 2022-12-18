package com.example.eal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.Squad;

import java.util.ArrayList;

public class DBManager {

    private FeedReaderDbHelper dbHelper;
    private Context context;
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;

    public DBManager(Context c) {
        this.context = c;
    }

    public DBManager open() throws SQLException {
        if (dbWrite == null && dbRead == null) {
            this.dbHelper = FeedReaderDbHelper.getInstance(context);
            this.dbWrite = dbHelper.getWritableDatabase();
            this.dbRead = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    //Spell
    public void addSpell(String IDEalard, String SpellName){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.EalardSpellEntry.COLUMN_NAME_IDEalard, IDEalard);
        values.put(FeedReaderContract.EalardSpellEntry.COLUMN_NAME_SpellName, SpellName);

        dbWrite.insert(FeedReaderContract.EalardSpellEntry.TABLE_NAME, null, values);
    }

    public void addSpellList(String IDEalard, ArrayList<String> SpellList){
        for( String spellName: SpellList) addSpell(IDEalard, spellName);
    }

    public void editSpell(String IDEalard, String SpellName){
        //Not Tested
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.EalardSpellEntry.COLUMN_NAME_IDEalard, IDEalard);
        values.put(FeedReaderContract.EalardSpellEntry.COLUMN_NAME_SpellName, SpellName);

        dbWrite.update(FeedReaderContract.EalardSpellEntry.TABLE_NAME, values,
                FeedReaderContract.EalardSpellEntry.COLUMN_NAME_IDEalard + " = ?", new String[]{IDEalard});
    }

    public void deleteSpell(String IDEalard, String SpellName){
        //Not Tested
        dbWrite.delete(FeedReaderContract.EalardSpellEntry.TABLE_NAME,
                FeedReaderContract.EalardSpellEntry.COLUMN_NAME_IDEalard + " = ? AND " +
                        FeedReaderContract.EalardSpellEntry.COLUMN_NAME_SpellName + " = ?",
                new String[]{IDEalard, SpellName});
    }

    public void deleteAllSpell(String IDEalard){
        dbWrite.delete(FeedReaderContract.EalardSpellEntry.TABLE_NAME,
                FeedReaderContract.EalardSpellEntry.COLUMN_NAME_IDEalard + " = ?",
                new String[]{IDEalard});
    }

    public ArrayList<String> getSpellList(String IDEalard){
        ArrayList<String> returnList = new ArrayList<>();

        Cursor cursor = dbRead.rawQuery("SELECT " + FeedReaderContract.EalardSpellEntry.COLUMN_NAME_SpellName +
                        " FROM " + FeedReaderContract.EalardSpellEntry.TABLE_NAME +
                        " WHERE " + FeedReaderContract.EalardSpellEntry.COLUMN_NAME_IDEalard + " = ?",
                new String[]{IDEalard});

        while(cursor.moveToNext()){
            returnList.add(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardSpellEntry.COLUMN_NAME_SpellName)));
        }
        cursor.close();

        return returnList;
    }


    //Ealard
    public void addEalard(Ealard e){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_IDEalard, e.getiDEalard());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_IDSquad, e.getiDSquad());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_Energy, e.getEnergy());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_Mobility, e.getMobility());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_Vitality, e.getVitality());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_Name, e.getName());

        dbWrite.insert(FeedReaderContract.EalardEntry.TABLE_NAME, null, values);

        addSpellList(e.getiDEalard(), e.getSpellList());
    }

    public void editEalard(Ealard e){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_IDEalard, e.getiDEalard());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_IDSquad, e.getiDSquad());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_Energy, e.getEnergy());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_Mobility, e.getMobility());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_Vitality, e.getVitality());
        values.put(FeedReaderContract.EalardEntry.COLUMN_NAME_Name, e.getName());

        dbWrite.update(FeedReaderContract.EalardEntry.TABLE_NAME, values,
                FeedReaderContract.EalardEntry.COLUMN_NAME_IDEalard + " = '" + e.getiDEalard() + "'", null);

        deleteAllSpell(e.getiDEalard());
        addSpellList(e.getiDEalard(), e.getSpellList());
    }

    public void deleteEalard(String IDEalard){
        //Don't forget to delete spell related to this Ealard
        deleteAllSpell(IDEalard);

        dbWrite.delete(FeedReaderContract.EalardEntry.TABLE_NAME,
                FeedReaderContract.EalardEntry.COLUMN_NAME_IDEalard + " = ?", new String[]{IDEalard});
    }

    public boolean isEalardInDatabase(String IDEalard){
        Cursor cursor = dbRead.rawQuery("SELECT * FROM "+
                FeedReaderContract.EalardEntry.TABLE_NAME +
                " WHERE " +
                FeedReaderContract.EalardEntry.COLUMN_NAME_IDEalard + " = ?", new String[]{IDEalard}, null);

        return cursor.moveToNext();
    }

    public Ealard getEalard(String IDEalard){
        Cursor cursor = dbRead.rawQuery("SELECT * FROM "+
                FeedReaderContract.EalardEntry.TABLE_NAME +
                " WHERE " +
                FeedReaderContract.EalardEntry.COLUMN_NAME_IDEalard + " = ?", new String[]{IDEalard}, null);

        if (!cursor.moveToNext()) return null;

        String IDSquad = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_IDSquad));
        String Name = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Name));
        int Energy = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Energy));
        int Mobility = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Mobility));
        int Vitality = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Vitality));

        cursor.close();

        ArrayList<String> SpellList = getSpellList(IDEalard);

        int essenceMax = getSquadGameMode(IDSquad).getNumberEssencePerEalard();

        return new Ealard(IDEalard, IDSquad, essenceMax, Name, Energy, Mobility, Vitality, SpellList);
    }


    //Squad
    public void addSquad(Squad squad){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad, squad.getIDSquad());
        values.put(FeedReaderContract.SquadEntry.COLUMN_NAME_Name, squad.getName());
        values.put(FeedReaderContract.SquadEntry.COLUMN_NAME_Mode, squad.getGameMode().getName());

        dbWrite.insert(FeedReaderContract.SquadEntry.TABLE_NAME, null, values);
    }

    public void editSquad(Squad squad){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad, squad.getIDSquad());
        values.put(FeedReaderContract.SquadEntry.COLUMN_NAME_Name, squad.getName());
        values.put(FeedReaderContract.SquadEntry.COLUMN_NAME_Mode, squad.getGameMode().getName());

        dbWrite.update(FeedReaderContract.SquadEntry.TABLE_NAME, values,
                FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad + " = ?",
                new String[]{squad.getIDSquad()});
    }

    public void deleteSquad(String IDSquad){
        //Delete all Member from the Squad
        ArrayList<Ealard> SquadEalard = getFromSquadEalards(IDSquad);
        for (Ealard ealard: SquadEalard) deleteEalard(ealard.getiDEalard());

        //If this Squad ID was in the buffer we need to delete it !
        GameMode gameMode;
        for(String gameModeName: GameMode.getListAllModeName()){
            gameMode = GameMode.getGameModeByName(gameModeName);
            if(IDSquad.equals(Squad.getBufferIDSquadByGameMode(gameMode)))
                Squad.setBufferIDSquadByGameMode(gameMode, null);
        }

        dbWrite.delete(FeedReaderContract.SquadEntry.TABLE_NAME,
                FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad + " = ?",
                new String[]{IDSquad});
    }

    public Squad getSquad(String IDSquad){
        Cursor cursor = dbRead.rawQuery("SELECT *" +
                        " FROM " +  FeedReaderContract.SquadEntry.TABLE_NAME +
                        " WHERE " + FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad + " = ?",
                new String[]{IDSquad});

        if(!cursor.moveToNext()) return null;

        String Name = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_Name));
        String gameModeName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_Mode));
        GameMode gameMode = GameMode.getGameModeByName(gameModeName);
        ArrayList<Ealard> members = getFromSquadEalards(IDSquad);
        cursor.close();

        return new Squad(IDSquad, Name, members, gameMode);
    }

    public GameMode getSquadGameMode(String IDSquad){
        Cursor cursor = dbRead.rawQuery("SELECT *" +
                        " FROM " +  FeedReaderContract.SquadEntry.TABLE_NAME +
                        " WHERE " + FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad + " = ?",
                new String[]{IDSquad});

        if(!cursor.moveToNext()) return null;

        String gameModeName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_Mode));
        GameMode gameMode = GameMode.getGameModeByName(gameModeName);
        cursor.close();

        return gameMode;
    }

    public ArrayList<Squad> getListSquad(){
        ArrayList<Squad> returnList = new ArrayList<>();

        String IDSquad;
        String Name;
        ArrayList<Ealard> members;


        Cursor cursor = dbRead.rawQuery("SELECT * FROM " +
                FeedReaderContract.SquadEntry.TABLE_NAME, null);

        while(cursor.moveToNext()){
            IDSquad = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad));
            Name = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_Name));
            String gameModeName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_Mode));
            GameMode gameMode = GameMode.getGameModeByName(gameModeName);

            members = getFromSquadEalards(IDSquad);

            returnList.add(new Squad(IDSquad, Name, members, gameMode));
        }

        cursor.close();

        return returnList;
    }

    public ArrayList<Squad> getListSquadWithGameMode(GameMode mode){
        ArrayList<Squad> returnList = new ArrayList<>();

        String IDSquad;
        String Name;
        ArrayList<Ealard> members;


        Cursor cursor = dbRead.rawQuery("SELECT * FROM " +
                FeedReaderContract.SquadEntry.TABLE_NAME +
                " WHERE " + FeedReaderContract.SquadEntry.COLUMN_NAME_Mode + " = ?",
                new String[]{mode.getName()});

        while(cursor.moveToNext()){
            IDSquad = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad));
            Name = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_Name));
            String gameModeName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_Mode));
            GameMode gameMode = GameMode.getGameModeByName(gameModeName);

            members = getFromSquadEalards(IDSquad);

            returnList.add(new Squad(IDSquad, Name, members, gameMode));
        }

        cursor.close();

        return returnList;
    }

    public ArrayList<String> getListSquadWithGameModeNames(GameMode mode){
        ArrayList<String> returnList = new ArrayList<>();

        String name;


        Cursor cursor = dbRead.rawQuery("SELECT * FROM " +
                        FeedReaderContract.SquadEntry.TABLE_NAME +
                        " WHERE " + FeedReaderContract.SquadEntry.COLUMN_NAME_Mode + " = ?",
                new String[]{mode.getName()});

        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.SquadEntry.COLUMN_NAME_Name));

            returnList.add(name);
        }

        cursor.close();

        return returnList;
    }

    public ArrayList<Ealard> getFromSquadEalards(String IDSquad){
        ArrayList<Ealard> returnList = new ArrayList<>();

        String IDEalard;
        String Name;
        int Energy;
        int Mobility;
        int Vitality;
        ArrayList<String> SpellList;

        Cursor cursor = dbRead.rawQuery("SELECT *" +
                        " FROM " + FeedReaderContract.EalardEntry.TABLE_NAME +
                        " WHERE " + FeedReaderContract.EalardEntry.COLUMN_NAME_IDSquad + " = ?",
                new String[]{IDSquad});

        while(cursor.moveToNext()){
            IDEalard = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_IDEalard));
            Name = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Name));
            Energy = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Energy));
            Mobility = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Mobility));
            Vitality = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Vitality));

            SpellList = getSpellList(IDEalard);

            int essenceMax = getSquadGameMode(IDSquad).getNumberEssencePerEalard();

            returnList.add(new Ealard(IDEalard, IDSquad, essenceMax, Name, Energy, Mobility, Vitality, SpellList));
        }

        cursor.close();

        return returnList;
    }

    public ArrayList<String> getFromSquadEalardsNames(String IDSquad){
        ArrayList<String> returnList = new ArrayList<>();

        String name;

        Cursor cursor = dbRead.rawQuery("SELECT *" +
                        " FROM " + FeedReaderContract.EalardEntry.TABLE_NAME +
                        " WHERE " + FeedReaderContract.EalardEntry.COLUMN_NAME_IDSquad + " = ?",
                new String[]{IDSquad});

        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.EalardEntry.COLUMN_NAME_Name));
            returnList.add(name);
        }

        cursor.close();

        return returnList;
    }

    public int getSquadSize(String IDSquad){
        //Return -1 if Squad doesn't exist

        Cursor cursor = dbRead.rawQuery("SELECT count(*)" +
                        " FROM " + FeedReaderContract.EalardEntry.TABLE_NAME +
                        " WHERE " + FeedReaderContract.EalardEntry.COLUMN_NAME_IDSquad + " = ?",
                new String[]{IDSquad});

        if (! cursor.moveToNext()) return -1;
        int size = cursor.getInt(0);

        cursor.close();

        return size;
    }
}