package com.example.eal.Activity.Play;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eal.Class.TurnManager.CountdownAction;
import com.example.eal.Class.TurnManager.TurnManager;
import com.example.eal.Adapter.InGamePageAdapter;
import com.example.eal.Application.App;
import com.example.eal.Class.GameMode.GameMode;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Map.Map;
import com.example.eal.Class.Player;
import com.example.eal.Class.Squad;
import com.example.eal.Database.DBManager;
import com.example.eal.Dialog.AreSureQuitGameDialog;
import com.example.eal.Dialog.CountdownActionsDialog;
import com.example.eal.Dialog.InGameSettingsDialog;
import com.example.eal.R;
import com.example.eal.databinding.ActivityPlayInGameBinding;

import java.util.ArrayList;

public class PlayInGameActivity extends AppCompatActivity implements CountdownActionsDialog.Listener, InGameSettingsDialog.Listener{
    private ActivityPlayInGameBinding binding;

    private static GameMode currentGameGameMode;
    private static ArrayList<Map> currentGameMaps;
    private static ArrayList<Player> currentGamePlayers;
    private static TurnManager currentGameTurnManager;

    private static InGameEntity activeInGameEntity;

    private static boolean gameEnded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayInGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setActivity(this);

        setToolbar();

        //set View Pager
        InGamePageAdapter pageAdapter = new InGamePageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, currentGamePlayers);
        binding.inGameViewpager.setAdapter(pageAdapter);

        binding.inGameTabs.setupWithViewPager(binding.inGameViewpager);
        binding.inGameTabs.getTabAt(currentGameTurnManager.getActivePlayerPosition()).select();
    }

    public void setToolbar(){
        updateTitle();

        ImageView back_icon = findViewById(R.id.tool_bar_return_icon);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(v->{
            AreSureQuitGameDialog dialog = AreSureQuitGameDialog.newInstance();
            dialog.show(getSupportFragmentManager(), null);
        });

        ImageView time_icon = findViewById(R.id.tool_bar_time_icon);
        time_icon.setVisibility(View.VISIBLE);
        time_icon.setOnClickListener(v-> countDownActionDialog());

        ImageView settings_icon = findViewById(R.id.tool_bar_option);
        settings_icon.setVisibility(View.VISIBLE);
        settings_icon.setOnClickListener(v->{
            InGameSettingsDialog dialog = InGameSettingsDialog.newInstance(this);
            dialog.show(getSupportFragmentManager(), null);
        });

    }

    public void countDownActionDialog(){
        CountdownActionsDialog dialog = CountdownActionsDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), null);
    }

    public void updateTitle(){
        TextView title = findViewById(R.id.tool_bar_title);
        Player p = getActivePlayer();
        title.setText(String.format("Round %d: it's %s turn", currentGameTurnManager.getRoundNumber(),p.getName()));
    }

    public static boolean checkEndGame(){
        if(gameEnded) return true;

        ArrayList<Player> playersHasntLost = new ArrayList<>();
        ArrayList<Player> playersHasWon = new ArrayList<>();
        for(Player p: currentGamePlayers){
            if(!p.hasLost())
                playersHasntLost.add(p);

            if(p.hasWon())
                playersHasWon.add(p);
        }
        if(playersHasntLost.size() <= 1){
            //If tie no winner are set
            for(Player p: playersHasntLost)
                getLastMap().setWinner(p);

        } else if (playersHasWon.size() == 1){
            getLastMap().setWinner(playersHasWon.get(0));
        } else if(playersHasWon.size() > 1){
            //If multiple player has won, it's a tie !
        } else{
            //If no condition are met we stay in game
            return false;
        }

        PlayInGameActivity.getLastMap().writeLineInHistoric("Game ended", Map.GAME_INDENTATION);
        gameEnded = true;

        Intent outOfActivityIntent = new Intent(App.getContext(), PlayChooseMapActivity.class);
        outOfActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(outOfActivityIntent);
        return true;
    }

    //CurrentGame attribute part
    public static ArrayList<Player> getCurrentGamePlayers() {
        return currentGamePlayers;
    }

    public static GameMode getCurrentGameGameMode() {
        return currentGameGameMode;
    }

    public static ArrayList<Map> getCurrentGameMaps() {
        return currentGameMaps;
    }

    public static void setCurrentGameGameMode(GameMode currentGameGameMode) {
        PlayInGameActivity.currentGameGameMode = currentGameGameMode;
    }

    public static void setCurrentGameMaps(ArrayList<Map> currentGameMaps) {
        PlayInGameActivity.currentGameMaps = currentGameMaps;
    }

    public static void setCurrentGamePlayers(ArrayList<Player> currentGamePlayers) {
        PlayInGameActivity.currentGamePlayers = currentGamePlayers;
    }

    public static Map getLastMap(){
        if(currentGameMaps.size() > 0)
            return currentGameMaps.get(currentGameMaps.size()-1);
        else
            return null;
    }

    public static void removeLastMap(){
        currentGameMaps.remove(currentGameMaps.size()-1);
    }

    public static int countNonPuppetPlayerWithoutInGameSquad(){
        int count = 0;
        for(Player p: currentGamePlayers){
            if(!p.isPuppetPlayer() && p.getOwnedInGameSquad() == null)
                count++;
        }
        return count;
    }

    public static Player getActivePlayer(){
        return currentGamePlayers.get(getCurrentGameTurnManager().getActivePlayerPosition());
    }

    private static int getFirstNonPuppetPlayerWithoutSquadId() {
        //Return -1 if every non puppet player has a squad
        for (int i = 0; i< currentGamePlayers.size(); i++) {
            Player p = currentGamePlayers.get(i);
            if (!p.isPuppetPlayer() && p.getOwnedSquadID() == null)
                return i;
        }
        return -1;
    }

    private static int getFirstNonPuppetPlayerWithoutInGameSquadPosition() {
        //Return -1 if every non puppet player has a squad
        for (int i = 0; i< currentGamePlayers.size(); i++) {
            Player p = currentGamePlayers.get(i);
            if (!p.isPuppetPlayer() && p.getOwnedInGameSquad() == null)
                return i;
        }
        return -1;
    }

    private static int getLastNonPuppetPlayerWithSquadIdPosition(){
        for(int i = currentGamePlayers.size()-1; i>=0; i--){
            Player p = currentGamePlayers.get(i);
            if(!p.isPuppetPlayer() && p.getOwnedSquadID() != null){
                return i;
            }
        }
        return -1;
    }

    private static int getLastNonPuppetPlayerWithInGameSquadPosition(){
        for(int i = currentGamePlayers.size()-1; i>=0; i--){
            Player p = currentGamePlayers.get(i);
            if(!p.isPuppetPlayer() && p.getOwnedInGameSquad() != null){
                return i;
            }
        }
        return -1;
    }

    public static void setPuppetInGameSquad(Map map, GameMode gameMode, TurnManager turnManager){
        for(Player p: currentGamePlayers){
            if(p.isPuppetPlayer())
                p.setOwnedInGameSquad(InGameSquad.getPuppetInGameSquad(map, gameMode, turnManager, p));
        }
    }

    public static boolean launchLastChooseSquadActivity(Context context){
        int positionLastPlayer = getLastNonPuppetPlayerWithSquadIdPosition();
        if(positionLastPlayer >= 0){
            currentGamePlayers.get(positionLastPlayer).setOwnedSquadID(null);

            Intent intent = new Intent(context, PlayChooseSquadActivitiy.class);
            intent.putExtra(PlayChooseSquadActivitiy.EXTRA_PLAYER_POSITION, positionLastPlayer);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public static boolean launchLastChooseEalardsActivity(Context context){
        int positionLastPlayer = getLastNonPuppetPlayerWithInGameSquadPosition();
        if(positionLastPlayer >= 0){
            currentGamePlayers.get(positionLastPlayer).setOwnedInGameSquad(null);

            Intent intent = new Intent(context, PlayChooseEalardsTransitionActivity.class);
            intent.putExtra(PlayChooseEalardsTransitionActivity.EXTRA_PLAYER_POSITION, positionLastPlayer);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public static boolean launchNextChooseSquadActivity(Context context){
        //return false if no squad has to be chosen
        int positionPlayerToFill = getFirstNonPuppetPlayerWithoutSquadId();
        if(positionPlayerToFill >= 0){
            Intent intent = new Intent(context, PlayChooseSquadActivitiy.class);
            intent.putExtra(PlayChooseSquadActivitiy.EXTRA_PLAYER_POSITION, positionPlayerToFill);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public static boolean launchNextChooseEalardActivity(Context context){
        //Return false if no ealards has to be chosen
        int positionPlayerToFill = getFirstNonPuppetPlayerWithoutInGameSquadPosition();
        if(positionPlayerToFill >= 0){
            if(fillPlayerInGameSquadWithSquad(positionPlayerToFill))
                return launchNextChooseEalardActivity(context);
            else{
                Intent intent = new Intent(context, PlayChooseEalardsTransitionActivity.class);
                intent.putExtra(PlayChooseEalardsTransitionActivity.EXTRA_PLAYER_POSITION, positionPlayerToFill);
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }

    public static boolean fillPlayerInGameSquadWithSquad(int positionPlayer){
        DBManager db = new DBManager(App.getContext());
        db.open();

        Player player = currentGamePlayers.get(positionPlayer);
        String squadId = player.getOwnedSquadID();
        if(squadId == null) return false;

        Squad squad = db.getSquad(player.getOwnedSquadID());
        db.close();

        //No need to choose ealards if all must be selected
        if(squad.getMembers().size() == getLastMap().getNumberEalard()){
            player.setOwnedInGameSquad(new InGameSquad(new ArrayList(squad.getMembers()), player, getCurrentGameTurnManager()));
            return true;
        }

        return false;
    }

    public static int getPositionLastPlayerWithLeastWin(){
        Player playerWithLeastWin = Map.getLastPlayerWithLeastWin(currentGamePlayers, currentGameMaps);
        return currentGamePlayers.indexOf(playerWithLeastWin);
    }

    public static int getPositionFirstPlayerWithLeastWin(){
        Player playerWithLeastWin = Map.getFirstPlayerWithLeastWin(currentGamePlayers, currentGameMaps);
        return currentGamePlayers.indexOf(playerWithLeastWin);
    }

    public static void resetInGameSquad(){
        for(Player p: currentGamePlayers){
            p.setOwnedInGameSquad(null);
        }
    }

    public static float getPlayerScore(Player player){
        float playerScore = 0;
        for(Map map: currentGameMaps){
            if(map.getWinner() == player)
                playerScore++;
            else if(map.getWinner() == null)
                playerScore+=0.5;
        }
        return playerScore;
    }

    public static void setGameEnded(boolean gameEnded) {
        PlayInGameActivity.gameEnded = gameEnded;
    }

    public static void setActiveInGameEntity(InGameEntity activeInGameEntity) {
        PlayInGameActivity.activeInGameEntity = activeInGameEntity;
    }

    public static boolean getGameEnded(){
        return gameEnded;
    }

    public static InGameEntity getActiveInGameEntity() {
        return activeInGameEntity;
    }

    public static TurnManager getCurrentGameTurnManager() {
        return currentGameTurnManager;
    }

    public static void reset(){
        currentGameGameMode = null;
        currentGameMaps = new ArrayList<>();
        currentGamePlayers = new ArrayList<>();
        currentGameTurnManager = new TurnManager(currentGamePlayers);
    }

    //CountdownActionsDialog.Listener
        @Override
        public ArrayList<CountdownAction> getCountdownActions() {
            return currentGameTurnManager.getCountdownActions();
        }

    @Override
    public Player activePlayer() {
        return getActivePlayer();
    }
}