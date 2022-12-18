package com.example.eal.Class.GameMode.GameModeDeathMatch;

import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.Player;

public abstract class GameModeDeathMatch extends GameMode {
    public GameModeDeathMatch(int numberPlayer, int numberPlayerMin, int numberPlayerMax, int numberEalardPerSquad, int numberEssencePerEalard, int numberRoadTo, String name, String mapRestrictionCriterion) {
        super(numberPlayer, numberPlayerMin, numberPlayerMax, numberEalardPerSquad, numberEssencePerEalard, numberRoadTo, name, mapRestrictionCriterion, null, null);

        if(getNumberEalardPerSquad() == 1){
            setSquadRestrictionCriterion("-1 ealard per squad");
        } else{
            setSquadRestrictionCriterion(String.format("-%s ealards per squad", getNumberEalardPerSquad()));
        }
        setEalardRestrictionCriterion(String.format("-Ealard have %s essence", getNumberEssencePerEalard()));
    }

    @Override
    public boolean hasPlayerLost(Player player) {
        return player.getOwnedInGameSquad().getInGameEalards().size() == 0;
    }

    @Override
    public boolean hasPlayerWon(Player player) {
        return false;
    }
}
