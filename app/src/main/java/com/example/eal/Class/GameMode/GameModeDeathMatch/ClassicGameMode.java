package com.example.eal.Class.GameMode.GameModeDeathMatch;

import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Squad;

public class ClassicGameMode extends GameModeDeathMatch {
    public static final String NAME = "Classic";
    private static final String MAP_RESTRICTION_CRITERION = "-Only Classic map are allowed";

    public ClassicGameMode(int numberPlayer, int numberroadTo) {
        super(numberPlayer, 2, 8, 4, 10, numberroadTo, NAME, MAP_RESTRICTION_CRITERION);
    }


    @Override
    protected boolean isMapValidAdditionalCriterion(Map map) {
        return true;
    }

    @Override
    public boolean isSquadAdditionalCriterion(Squad squad) {
        return true;
    }

    @Override
    public boolean isEalardValidAdditionalCriterion(Ealard ealard) {
        return true;
    }
}
