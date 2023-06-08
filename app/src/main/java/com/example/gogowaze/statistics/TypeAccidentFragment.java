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

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeAccidentFragment extends Fragment implements Accident.Observer  {
    private static final String ARG_ACCIDENT_DATA = "accidentData";


    private String currentCity = null;

    private static final String ARG_ACCIDENT_CONTROLLER = "accidentController";

    public TypeAccidentFragment() {
        // Constructeur public vide requis par Android
    }

    public static TypeAccidentFragment newInstance() {
        TypeAccidentFragment fragment = new TypeAccidentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Créer la vue du fragment à partir du layout XML

        return inflater.inflate(R.layout.fragment_type_accident, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public void updateView(Map<String,Integer> typeAccident) {

        if (getView() != null) {
            TableLayout tableLayout = getView().findViewById(R.id.table_layout);

            // Utiliser le contrôleur pour obtenir les données nécessaires
            List<Accident> accidents = new ArrayList<>();

            fillTableWithData(tableLayout, typeAccident);
        }

    }






    public void fillTableWithData(TableLayout tableLayout, Map<String, Integer> typeAccident) {
        // Effacer toutes les vues existantes du tableau
        tableLayout.removeAllViews();

        // Parcourir la map et ajouter les lignes au tableau
        for (Map.Entry<String, Integer> entry : typeAccident.entrySet()) {
            // Créer une nouvelle TableRow
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Créer un TextView pour le type d'accident
            TextView typeAccidentTextView = new TextView(getContext());
            typeAccidentTextView.setTextSize(14);
            typeAccidentTextView.setText(entry.getKey()); // utiliser la clé de la map comme type d'accident
            typeAccidentTextView.setPadding(10, 10, 10, 10);
            tableRow.addView(typeAccidentTextView);

            // Créer un TextView pour le nombre d'accidents
            TextView countAccidentTextView = new TextView(getContext());
            countAccidentTextView.setTextSize(14);
            countAccidentTextView.setText(String.valueOf(entry.getValue())); // utiliser la valeur de la map comme compte d'accident
            countAccidentTextView.setPadding(10, 10, 10, 10);
            tableRow.addView(countAccidentTextView);

            // Ajouter la TableRow au TableLayout
            tableLayout.addView(tableRow);
        }
    }


    @Override
    public void onAccidentChanged(Accident accident) {

    }
}
