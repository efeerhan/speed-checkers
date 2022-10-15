package com.crunglers.speedcheckers;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class selectFragment extends Fragment {

    public selectFragment() {
        super(R.layout.fragment_select);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle args) {
        super.onCreateView(inflater, container, args);

        View view = inflater.inflate(R.layout.fragment_select, container, false);
        TransitionInflater tInflater = TransitionInflater.from(requireContext());
        setEnterTransition(tInflater.inflateTransition(R.transition.slide_right));

        view.findViewById(R.id.HostSelect).setOnClickListener( v -> {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.slide_out
                    )
                    .replace(R.id.startFragment, new hostGameFragment())
                    .addToBackStack(null)
                    .commit();
        });

        view.findViewById(R.id.FindSelect).setOnClickListener( v -> {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.slide_out
                    )
                    .replace(R.id.startFragment, new findGameFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}