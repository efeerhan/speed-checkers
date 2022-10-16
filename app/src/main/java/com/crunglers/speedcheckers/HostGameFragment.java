package com.crunglers.speedcheckers;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class HostGameFragment extends Fragment {

    public HostGameFragment() {
        super(R.layout.fragment_host_game);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle args) {
        super.onCreateView(inflater, container, args);
        View view = inflater.inflate(R.layout.fragment_host_game, container, false);
        TransitionInflater tInflater = TransitionInflater.from(requireContext());
        setExitTransition(tInflater.inflateTransition(R.transition.slide_left));
        setEnterTransition(tInflater.inflateTransition(R.transition.slide_right));


        return view;
    }
}