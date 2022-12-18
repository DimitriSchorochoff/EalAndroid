package com.example.eal.Class.Spell.Element;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Class.Entities.Invocation.Creature.BloodSphere;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameInvocation.InGameBloodSphere;
import com.example.eal.Class.InGame.InGameInvocation.InGameInvocation;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.TurnManager.CountdownClearBloodSacrifice;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Dialog.MessageDialog;

import java.util.ArrayList;

public class Blood extends Element implements MessageDialog.Listener {
    private static final String ELEMENT = "Blood";
    public static final int damageOnSacrifice = 5;

    private static ArrayList<InGameEntity> madeBSList = new ArrayList<>();

    @Override
    public String getElementName() {
        return ELEMENT;
    }

    public static String getELEMENT() {
        return ELEMENT;
    }

    public static void clearBloodSacrificeList(){
        madeBSList.removeAll(madeBSList);
    }

    public static boolean hasMadeBloodSacrifice(InGameEntity sacrifiedEntity){
        return madeBSList.contains(sacrifiedEntity);
    }

    public void makeBloodSacrifice(AppCompatActivity activity, InGameEntity sacrifiedEntity, TurnManager turnManager){
        if(sacrifiedEntity instanceof InGameBloodSphere)
            return;
        if (hasMadeBloodSacrifice(sacrifiedEntity))
            return;

        PlayInGameActivity.getLastMap().writeLineInHistoric(String.format("%s has made a blood sacrifice", sacrifiedEntity.getCompleteName()), Map.EFFECT_INDENTATION);

        if(!turnManager.containsClearBloodSacrificeAction())
            turnManager.addCountdownAction(new CountdownClearBloodSacrifice(turnManager));

        madeBSList.add(sacrifiedEntity);
        sacrifiedEntity.takeTrueDamage(damageOnSacrifice);

        InGameBloodSphere bloodSphere = InGameBloodSphere.getBloodSphere(sacrifiedEntity);

        if(bloodSphere == null)
            summonBloodSphere(activity, sacrifiedEntity);
        else
            bloodSphere.onBloodSacrifice();
    }

    private InGameInvocation summonBloodSphere(AppCompatActivity activity, InGameEntity summoner){
        InGameSquad squad = summoner.getInGameSquad();
        InGameBloodSphere returnInvocation = new InGameBloodSphere(new BloodSphere(), summoner);
        squad.addInGameEntity(returnInvocation);

        MessageDialog dialog = MessageDialog.newInstance(this);
        dialog.show(activity.getSupportFragmentManager(), null);

        return returnInvocation;
    }

    @Override
    public String getMessage() {
        return "A blood sphere has been summoned, place it around the sacrified summoner";
    }

    @Override
    public String getAdditionalMessage() {
        return null;
    }

    @Override
    public void onQuitMessageDialog() {}
}
