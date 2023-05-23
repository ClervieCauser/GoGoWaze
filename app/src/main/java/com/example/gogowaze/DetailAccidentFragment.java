package com.example.gogowaze;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DetailAccidentFragment extends Fragment {

    private Button btnFermer;

    public DetailAccidentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_accident, container, false);

        btnFermer = view.findViewById(R.id.buttonExit);
        btnFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fermez le fragment
                getActivity().getSupportFragmentManager().beginTransaction().remove(DetailAccidentFragment.this).commit();
            }
        });

        return view;
    }

}