package com.example.gogowaze;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SignalFragment extends Fragment {

    public SignalFragment() {
        // Required empty public constructor
    }

    private Button btnFermer;
    private Button btnAnnuler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signal, container, false);

        btnFermer = view.findViewById(R.id.buttonExit);
        btnAnnuler = view.findViewById(R.id.annulerSignalement);
        btnFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fermez le fragment
                getActivity().getSupportFragmentManager().beginTransaction().remove(SignalFragment.this).commit();
            }
        });

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fermez le fragment
                getActivity().getSupportFragmentManager().beginTransaction().remove(SignalFragment.this).commit();
            }
        });

        return view;
    }

}