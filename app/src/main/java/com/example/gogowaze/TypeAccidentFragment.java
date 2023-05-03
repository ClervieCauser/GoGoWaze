package com.example.gogowaze;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gogowaze.R;

public class TypeAccidentFragment extends Fragment {
    public TypeAccidentFragment() {
        // Constructeur public vide requis par Android
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Créer la vue du fragment à partir du layout XML
        View view = inflater.inflate(R.layout.fragment_type_accident, container, false);

        // Effectuer toute opération d'initialisation supplémentaire ici, si nécessaire

        // Retourner la vue du fragment
        return view;
    }
}
