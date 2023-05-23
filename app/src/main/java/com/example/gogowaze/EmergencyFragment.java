package com.example.gogowaze;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EmergencyFragment extends Fragment implements View.OnClickListener  {
    private Button mButton15;
    private Button btnFermer;

    public EmergencyFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);

        mButton15 = view.findViewById(R.id.button_15);
        mButton15.setOnClickListener(this);

        btnFermer = view.findViewById(R.id.close_button);
        btnFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fermez le fragment
                getActivity().getSupportFragmentManager().beginTransaction().remove(EmergencyFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mButton15) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:15"));
            startActivity(intent);
        }
    }
}