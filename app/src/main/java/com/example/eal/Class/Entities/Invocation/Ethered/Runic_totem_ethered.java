package com.example.eal.Class.Entities.Invocation.Ethered;

import java.util.ArrayList;

public class Runic_totem_ethered extends Ethered{
    private static final String NAME = "Runic totem";

    private static final ArrayList<String> spellList = new ArrayList();


    public Runic_totem_ethered() {
        super(0,
                4,
                "Can be used as a rune target",
                spellList);
    }

    public static String getNAME(){
        return NAME;
    }
    @Override
    public String getName() {
        return NAME;
    }


}
