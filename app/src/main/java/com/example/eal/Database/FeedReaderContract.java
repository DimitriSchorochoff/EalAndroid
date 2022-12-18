package com.example.eal.Database;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    // Inner class that defines the table contents
    public static class SquadEntry implements BaseColumns {
        public static final String TABLE_NAME = "Squad";
        public static final String COLUMN_NAME_IDSquad = "IDSquad";
        public static final String COLUMN_NAME_Name = "Name";
        public static final String COLUMN_NAME_Mode = "Mode";
    }

    public static class EalardEntry implements BaseColumns {
        public static final String TABLE_NAME = "Ealard";
        public static final String COLUMN_NAME_IDEalard = "IDEalard";
        public static final String COLUMN_NAME_IDSquad = "IDSquad";
        public static final String COLUMN_NAME_Name = "Name";
        public static final String COLUMN_NAME_Energy = "Energy";
        public static final String COLUMN_NAME_Mobility = "Mobility";
        public static final String COLUMN_NAME_Vitality = "Vitality";
    }

    public static class EalardSpellEntry implements BaseColumns {
        public static final String TABLE_NAME = "EalardSpell";
        public static final String COLUMN_NAME_IDEalard = "IDEalard";
        public static final String COLUMN_NAME_SpellName = "SpellName";
    }
}