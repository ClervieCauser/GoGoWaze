package com.example.gogowaze;

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

import org.json.JSONException;

import java.util.List;
public class GraviteFragment extends Fragment {
    private static final String ARG_ACCIDENT_DATA = "accidentData";
    private AccidentData accidentData;

    public GraviteFragment() {
        // Constructeur public vide requis par Android
    }

    public static GraviteFragment newInstance(AccidentData accidentData) {
        GraviteFragment fragment = new GraviteFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACCIDENT_DATA, accidentData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Créer la vue du fragment à partir du layout XML
        return inflater.inflate(R.layout.fragment_gravity, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ici", " je vais dans bundle");

        Bundle bundle = getArguments();

        if (bundle != null) {
            Log.d("ici", "dans bundle");

            accidentData = (AccidentData) bundle.getSerializable(ARG_ACCIDENT_DATA);
            Log.d("ici", accidentData.toString());

            if (accidentData != null) {
                Log.d("ici", "dans accident data");
                // Récupérer le TableLayout et le remplir avec les données
                TableLayout tableLayout = getView().findViewById(R.id.table_layout);
                fillTableWithData(tableLayout, "null");
            }
        }
    }

    public void fillTableWithData(TableLayout tableLayout, String city) {
        // Remplacez "city" par le nom de la ville pour laquelle vous souhaitez afficher les données
        tableLayout.removeAllViews();
        try {
            List<Accident> accidents = accidentData.getGravityListForCity(city);
            Log.d("ici", "ICIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
            Log.d("yo", accidents.toString());
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
        } catch (JSONException e) {
            e.printStackTrace();
            // Gérer l'erreur ici, par exemple en affichant un message d'erreur à l'utilisateur
        }
    }
}
