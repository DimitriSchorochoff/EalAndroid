package com.example.eal.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Application.App;
import com.example.eal.Class.Entities.Ealard;
import com.example.eal.Class.InGame.InGameEalard;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.InGame.InGameSquad;
import com.example.eal.Class.Player;
import com.example.eal.Fragment.SquadInGameFragment;
import com.example.eal.R;

import java.util.ArrayList;

public class InGamePageAdapter extends FragmentPagerAdapter {
    private ArrayList<Player> players;

    public InGamePageAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Player> players) {
        super(fm, behavior);
        this.players = players;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return SquadInGameFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position){
        String playerName = players.get(position).getName();
        if(position == PlayInGameActivity.getCurrentGameTurnManager().getStartRoundPlayerPosition())
            return String.format("• %s", playerName);
        else
            return playerName;
    }

    @Override
    public int getCount() {
        return players.size();
    }
}
