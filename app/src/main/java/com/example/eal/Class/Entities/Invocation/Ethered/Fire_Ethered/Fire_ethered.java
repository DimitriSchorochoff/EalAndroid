package com.example.eal.Class.Entities.Invocation.Ethered.Fire_Ethered;

import com.example.eal.Class.Entities.Invocation.Ethered.Ethered;

import java.util.ArrayList;

public abstract class Fire_ethered extends Ethered {
    private boolean enhanced;

    public Fire_ethered(int base_energy, int base_mobility, String additionalInformation, ArrayList<String> baseSpellList) {
        super(base_energy, base_mobility, additionalInformation, baseSpellList);
        this.enhanced = false;
    }

    public void on_enhance(){
        if(!enhanced){
            enhanced = true;
        }
    }


}
