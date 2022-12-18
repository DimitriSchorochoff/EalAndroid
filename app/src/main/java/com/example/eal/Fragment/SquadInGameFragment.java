package com.example.eal.Fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.eal.Activity.Play.PlayInGameActivity;
import com.example.eal.Adapter.InGameEntitiesAdapter;
import com.example.eal.R;

public class SquadInGameFragment extends Fragment {

    private static final String EXTRA_PLAYER_POSITION = "PlayerPosition";

    private int playerPosition;

    private InGameEntitiesAdapter adapter;

    //Animation
    private boolean animationShown;
    private ConstraintLayout mainLayout;
    private ScrollView scrollView;

    public SquadInGameFragment() {
        // Required empty public constructor
    }


    public static SquadInGameFragment newInstance(int position) {
        SquadInGameFragment fragment = new SquadInGameFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PLAYER_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.playerPosition = getArguments().getInt(EXTRA_PLAYER_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_squad_in_game, container, false);



        RecyclerView entityRecycler = view.findViewById(R.id.fragment_squad_in_game_recycler);
        entityRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.adapter = new InGameEntitiesAdapter(getContext(), playerPosition);
        entityRecycler.setAdapter(adapter);


        animationShown = false;
        mainLayout = view.findViewById(R.id.fragment_squad_in_game_constraint_layout);
        scrollView = view.findViewById(R.id.fragment_squad_in_game_historic_scrollView);
        //Set scroll to bottom
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        ImageButton historicButton = view.findViewById(R.id.fragment_squad_in_game_historic_button);
        historicButton.setOnClickListener(v->{
            if(animationShown){
                historicButton.setImageResource(R.drawable.ic_expand_less_icon);
               revertAnimation();
            } else{
                historicButton.setImageResource(R.drawable.ic_expand_more_icon);
                showAnimation();
            }
        });
        TextView historicTv = view.findViewById(R.id.fragment_squad_in_game_historic_tV);
        historicTv.setText(PlayInGameActivity.getLastMap().getMatchHistoric());


        return view;
    }

    private void showAnimation() {
        animationShown = true;

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(getContext(), R.layout.fragment_squad_in_game_historic_animation);

        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateInterpolator(1.0f));
        transition.setDuration(1200);

        TransitionManager.beginDelayedTransition(mainLayout, transition);
        constraintSet.applyTo(mainLayout);
    }

    private void revertAnimation() {
        animationShown = false;

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(getContext(), R.layout.fragment_squad_in_game);

        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateInterpolator(1.0f));
        transition.setDuration(1200);

        TransitionManager.beginDelayedTransition(mainLayout, transition);
        constraintSet.applyTo(mainLayout);

    }
}