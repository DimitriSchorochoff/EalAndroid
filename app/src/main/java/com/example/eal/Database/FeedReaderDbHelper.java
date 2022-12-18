package com.example.eal.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {


    private static FeedReaderDbHelper sInstance;

    public static final String SQL_CREATE_Squad_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.SquadEntry.TABLE_NAME + " (" +
                    FeedReaderContract.SquadEntry.COLUMN_NAME_IDSquad + " TEXT PRIMARY KEY, " +
                    FeedReaderContract.SquadEntry.COLUMN_NAME_Name + " TEXT," +
                    FeedReaderContract.SquadEntry.COLUMN_NAME_Mode + " TEXT)";

    public static final String SQL_CREATE_EALARD_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.EalardEntry.TABLE_NAME + " (" +
                    FeedReaderContract.EalardEntry.COLUMN_NAME_IDEalard + " TEXT PRIMARY KEY, " +
                    FeedReaderContract.EalardEntry.COLUMN_NAME_IDSquad + " TEXT, " +
                    FeedReaderContract.EalardEntry.COLUMN_NAME_Name + " TEXT, " +
                    FeedReaderContract.EalardEntry.COLUMN_NAME_Energy + " INTEGER, " +
                    FeedReaderContract.EalardEntry.COLUMN_NAME_Mobility + " INTEGER, " +
                    FeedReaderContract.EalardEntry.COLUMN_NAME_Vitality + " INTEGER)";

    public static final String SQL_CREATE_EALARDSPELL_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.EalardSpellEntry.TABLE_NAME + " (" +
                    FeedReaderContract.EalardSpellEntry.COLUMN_NAME_IDEalard + " TEXT, " +
                    FeedReaderContract.EalardSpellEntry.COLUMN_NAME_SpellName + " TEXT)";


    private static final String SQL_DELETE_Squad_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.SquadEntry.TABLE_NAME;

    private static final String SQL_DELETE_EALARD_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.EalardEntry.TABLE_NAME;

    private static final String SQL_DELETE_EALARDSPELL_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.EalardSpellEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "FeedReader.db";


    public static synchronized FeedReaderDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FeedReaderDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_Squad_ENTRIES);
        db.execSQL(SQL_CREATE_EALARD_ENTRIES);
        db.execSQL(SQL_CREATE_EALARDSPELL_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_Squad_ENTRIES);
        db.execSQL(SQL_DELETE_EALARD_ENTRIES);
        db.execSQL(SQL_DELETE_EALARDSPELL_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}