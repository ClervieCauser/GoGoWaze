package com.example.gogowaze.statistics;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gogowaze.model.Accident;
import com.example.gogowaze.R;
import com.example.gogowaze.statistics.controller.AccidentController;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GraviteFragment extends Fragment implements Accident.Observer {


    private String currentCity = null;

    public GraviteFragment() {
        // Constructeur public vide requis par Android
    }

    public static GraviteFragment newInstance() {
        GraviteFragment fragment = new GraviteFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Créer la vue du fragment à partir du layout XML
        return inflater.inflate(R.layout.fragment_gravity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (currentCity != null) {
            updateView(currentCity);
        }
    }



    public void updateView(String city) {

        currentCity = city;
        if (getView() != null) {
            TableLayout tableLayout = getView().findViewById(R.id.table_layout);

            // Utiliser le contrôleur pour obtenir les données nécessaires
            //List<Accident> accidents = accidentController.getGravityListForCity(city);
            List<Accident> accidents = new ArrayList<>();

            // Remplir le tableau avec les données obtenues
            fillTableWithData(tableLayout, accidents);
        }

    }

    @Override
    public void onAccidentChanged(Accident accident) {
        // Mettre à jour le modèle ou la vue en conséquence
        Log.d("Accident", "Accident changed: " + accident.getType() + " " + accident.getCount());
        if (getView() != null) {
            TableLayout tableLayout = getView().findViewById(R.id.table_layout);
            List<Accident> accidents = new ArrayList<>();

            fillTableWithData(tableLayout, accidents);
        }

    }




    public void fillTableWithData(TableLayout tableLayout, List<Accident> accidents) {
        // Remplacez "city" par le nom de la ville pour laquelle vous souhaitez afficher les données
        tableLayout.removeAllViews();
        // Parcourir la liste des accidents et ajouter les lignes au tableau
        for (Accident accident : accidents) {
            // Créer une nouvelle TableRow
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Créer un TextView pour le type d'accident
            TextView typeAccidentTextView = new TextView(getContext());
            typeAccidentTextView.setText(accident.getType());
            typeAccidentTextView.setPadding(10, 10, 10, 10);
            tableRow.addView(typeAccidentTextView);

            // Créer un TextView pour le nombre d'accidents
            TextView countAccidentTextView = new TextView(getContext());
            countAccidentTextView.setText(String.valueOf(accident.getCount()));
            countAccidentTextView.setPadding(10, 10, 10, 10);
            tableRow.addView(countAccidentTextView);

            // Ajouter la TableRow au TableLayout
            tableLayout.addView(tableRow);
        }
    }


}
