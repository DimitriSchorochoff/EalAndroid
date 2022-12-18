package com.example.eal.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eal.Adapter.PlayerAndTargetsAdapter;
import com.example.eal.Class.InGame.InGameEntity;
import com.example.eal.Class.Player;
import com.example.eal.Class.Spell.TargetedSpell;
import com.example.eal.databinding.DialogTargetBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class TargetDialog extends DialogFragment implements AreSureDialog.Listener{
    private Listener listener;

    private DialogTargetBinding binding;

    private ArrayList<Player> players;
    private InGameEntity caster;

    private HashMap<InGameEntity, Integer> targets;
    private TargetedSpell spell;
    private int maximumNumberTargets;

    protected TargetDialog(){
        //Need to be empty
    }

    public static TargetDialog newInstance(Listener listener) {
        TargetDialog fragment = new TargetDialog();
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        binding = DialogTargetBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());

        players = (ArrayList<Player>) listener.getPlayers().clone();
        caster = listener.getCaster();
        targets = new HashMap<>();
        spell = listener.getTargetedSpell();
        maximumNumberTargets = spell.getArea().getMaximumNumberTarget();

        putCasterOwnerOnFirstPlace();

        if(listener.extraCast())
            binding.dialogTargetQuitImageView.setVisibility(View.GONE);
        binding.dialogTargetQuitImageView.setOnClickListener(view->{
            listener.onCancel();
            TargetDialog.this.getDialog().cancel();
        });

        binding.dialogTargetSpellname.setText(
                String.format("%s: %s", caster.getCompleteName(),spell.getSpellName()));

        binding.dialogTargetPlayerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        PlayerAndTargetsAdapter adapter = new PlayerAndTargetsAdapter(getContext(), players, caster, targets, this, spell.getTargetFilter());
        binding.dialogTargetPlayerRecycler.setAdapter(adapter);

        majValue();

        binding.dialogTargetValidateButton.setOnClickListener(view->{
            if(targets.size() == 0){
                AreSureDialog areSureNoTargetDialog = AreSureDialog.newInstance(this);
                areSureNoTargetDialog.show(getActivity().getSupportFragmentManager(), null);
            }  else if (targets.size() > maximumNumberTargets){
                Toast.makeText(getContext(),
                        String.format("You picked to many target for you're area: %d/%d", targets.size(), maximumNumberTargets),
                        Toast.LENGTH_SHORT).show();
            } else{
                validateTargets();
            }
        });

        return builder.create();
    }

    public void putCasterOwnerOnFirstPlace(){
        Player casterOwner = caster.getInGameSquad().getPlayer();
        players.remove(casterOwner);
        players.add(0, casterOwner);
    }

    public void filterInGameEntity(){

    }

    public void majValue(){
        if(maximumNumberTargets == 1){
            binding.dialogTargetNumberTargetTextView.setText(
                    String.format("%d/1", targets.size()));
        } else{
            binding.dialogTargetNumberTargetTextView.setText(Integer.toString(targets.size()));
        }
    }

    private void validateTargets(){
        listener.setTargets(targets);
        TargetDialog.this.getDialog().cancel();
    }

    @Override
    public String getDialogMessage() {
        return "You have no targets";
    }

    @Override
    public void onYesAction() {
        validateTargets();
    }

    public boolean isSpellMultiHit(){
        return spell.isMultiHit();
    }

    public interface Listener{
        ArrayList<Player> getPlayers();
        InGameEntity getCaster();
        void setTargets(HashMap<InGameEntity, Integer> targets);
        TargetedSpell getTargetedSpell();
        boolean extraCast();
        void onCancel();
    }
}
