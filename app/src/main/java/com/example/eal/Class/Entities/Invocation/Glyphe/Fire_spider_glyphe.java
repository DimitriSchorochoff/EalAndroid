package com.example.eal.Class.Entities.Invocation.Glyphe;

import java.util.ArrayList;
import java.util.Arrays;

public class Fire_spider_glyphe extends Glyphe{
    private static final String INVOCATIONNAME = "Fire spider";

    private static final ArrayList<String> spellList = new ArrayList(Arrays.asList("Spider_bite"));

    public Fire_spider_glyphe(){
        super(0,
                3,
                null,
                spellList);
    }

    @Override
    public String getName() {
        return INVOCATIONNAME;
    }

    public static String getINVOCATIONNAME() {
        return INVOCATIONNAME;
    }
}
