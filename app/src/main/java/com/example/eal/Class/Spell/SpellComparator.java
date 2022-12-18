package com.example.eal.Class.Spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SpellComparator {
    public static void sortByCompareType(ArrayList<String> listToSort, CompareType type, boolean ascSorting){
        //Start by sorting by name to keep sorting consistency
        Collections.sort(listToSort, getCompareSpellByNameAsc());

        switch (type){
            case NAME:
                if(ascSorting)
                    Collections.sort(listToSort, getCompareSpellByNameAsc());
                else
                    Collections.sort(listToSort, getCompareSpellByNameDesc());
                break;
            case ELEMENT:
                if(ascSorting)
                    Collections.sort(listToSort, getCompareSpellByElementAsc());
                else
                    Collections.sort(listToSort, getCompareSpellByElementDesc());
                break;
            case EFFECT:
                if(ascSorting)
                    Collections.sort(listToSort, getCompareSpellByEffectAsc());
                else
                    Collections.sort(listToSort, getCompareSpellByEffectDesc());
                break;
            case ENERGY:
                if(ascSorting)
                    Collections.sort(listToSort, getCompareSpellByEnergyAsc());
                else
                    Collections.sort(listToSort, getCompareSpellByEnergyDesc());
                break;
        }
    }

    public static Comparator<String> getCompareSpellByNameAsc(){
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                return o1.compareTo(o2);
            }
        };
    }

    public static Comparator<String> getCompareSpellByEffectAsc() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Spell s1 = Spell.getSpell(o1);
                Spell s2 = Spell.getSpell(o2);

                return s1.getEffect().compareTo(s2.getEffect());
            }
        };
    }

    public static Comparator<String> getCompareSpellByEnergyAsc() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Spell s1 = Spell.getSpell(o1);
                Spell s2 = Spell.getSpell(o2);

                return s1.getEnergy_cost() - s2.getEnergy_cost();
            }
        };
    }

    public static Comparator<String> getCompareSpellByElementAsc() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Spell s1 = Spell.getSpell(o1);
                Spell s2 = Spell.getSpell(o2);

                return s1.getElementName().compareTo(s2.getElementName());
            }
        };
    }

    public static Comparator<String> getCompareSpellByNameDesc(){
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                return o2.compareTo(o1);
            }
        };
    }

    public static Comparator<String> getCompareSpellByEffectDesc() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Spell s1 = Spell.getSpell(o1);
                Spell s2 = Spell.getSpell(o2);

                return s2.getEffect().compareTo(s1.getEffect());
            }
        };
    }

    public static Comparator<String> getCompareSpellByEnergyDesc() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Spell s1 = Spell.getSpell(o1);
                Spell s2 = Spell.getSpell(o2);

                return s2.getEnergy_cost() - s1.getEnergy_cost();
            }
        };
    }

    public static Comparator<String> getCompareSpellByElementDesc() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Spell s1 = Spell.getSpell(o1);
                Spell s2 = Spell.getSpell(o2);

                return s2.getElementName().compareTo(s1.getElementName());
            }
        };
    }

    public enum CompareType{
        NAME,
        EFFECT,
        ENERGY,
        ELEMENT
    }
}
