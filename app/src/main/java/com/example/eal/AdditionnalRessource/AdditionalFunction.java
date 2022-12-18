package com.example.eal.AdditionnalRessource;

import android.app.Activity;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AdditionalFunction {
    public static Object deepCopy(Object object) {
        //All credit to https://www.journaldev.com/17129/java-deep-copy-object
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
            outputStrm.writeObject(object);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            return objInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void hideKeyboard(Activity activity) {
        //All credit to rmirabelle from https://stackoverflow.com/questions/1109022/how-do-you-close-hide-the-android-soft-keyboard-using-java
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    public static Pair<String,Integer> getPrefixAndNumber(String name){
        //This will split string with format: "%s_%d".
        //If string doesn't match the format return (string, 1)
        String[] splitSquadName = name.split("_");
        if (splitSquadName.length == 1) return new Pair<>(name,1);

        try{
            int number = Integer.parseInt(splitSquadName[splitSquadName.length-1]);

            ArrayList<String> prefixList = new ArrayList<>();
            for(int i = 0; i<splitSquadName.length-1; i++)
                prefixList.add(splitSquadName[i]);
            String prefix = String.join("_", prefixList);

            return new Pair<>(prefix, number);
        } catch (NumberFormatException e){
            return new Pair<>(name, 1);
        }
    }


    public static String getNextValidNumberedName(ArrayList<String> usedNames, String name){
        if(!usedNames.contains(name))
            return name;



        Pair<String, Integer> originalPrefixAndNumber = AdditionalFunction.getPrefixAndNumber(name);
        ArrayList<Integer> usedNumbers = new ArrayList<>();

        Pair<String, Integer> prefixAndNumber;
        for(String usedName: usedNames){
            prefixAndNumber = AdditionalFunction.getPrefixAndNumber(usedName);
            if(prefixAndNumber.first.equals(originalPrefixAndNumber.first))
                usedNumbers.add(prefixAndNumber.second);
        }

        usedNumbers.sort(Integer::compareTo);
        int nextValidNumber = 2;
        for(int usedNumber: usedNumbers){
            if(nextValidNumber < usedNumber)
                break;

            nextValidNumber = usedNumber+1;
        }


        return originalPrefixAndNumber.first + "_" + Integer.toString(nextValidNumber);
    }

    public static boolean containsOnlySpace(String string){
        for(int i = 0; i<string.length(); i++){
            if(string.charAt(i) != ' ')
                return false;
        }
        return true;
    }

    public static <K> float getHighestValue(HashMap<K, Float> hashMap){
        float maximumValue = Float.MIN_VALUE;

        for(float value: hashMap.values())
            maximumValue = Math.max(maximumValue, value);

        return maximumValue;
    }

    public static <K> HashMap<K, Float> keepHighValue(HashMap<K, Float> hashMap, float valueToBeOver){
        HashMap<K, Float> copiedHashMap = (HashMap<K, Float>) hashMap.clone();

        for(K key: hashMap.keySet()){
            if(copiedHashMap.get(key) < valueToBeOver)
                copiedHashMap.remove(key);
        }

        return copiedHashMap;
    }
}
