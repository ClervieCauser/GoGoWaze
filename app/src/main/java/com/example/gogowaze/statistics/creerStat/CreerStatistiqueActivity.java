package com.example.gogowaze.statistics.creerStat;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gogowaze.R;
import com.example.gogowaze.model.GraviteListModel;
import com.example.gogowaze.statistics.creerStat.controller.ControllerGravite;
import com.example.gogowaze.statistics.creerStat.view.ViewCreerStatistiques;

public class CreerStatistiqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_statistiques);
        onViewCreated( findViewById(R.id.view_creerStatistiques) );

    }

    public <T extends ViewGroup> void onViewCreated(T layout) {
        // Créer la VUE avec le layout XML
        ViewCreerStatistiques view = new ViewCreerStatistiques(layout);
        GraviteListModel modelList = new GraviteListModel();    // Le contrôleur n'est pas encore créé donc la référence du contrôleur sera envoyée plus tard
        modelList.addObserver(view);    // Le MODELE est observable par la VUE

        ControllerGravite controller = new ControllerGravite(view, modelList);
        CreerStatAdapter adapter = new CreerStatAdapter(getApplicationContext(), controller, modelList, view);

        view.setAdapter(adapter);
        modelList.setController(controller);    // envoyé pour le principe mais dans cet exercice, le MODELE n'a pas besoin du contrôleur
        view.setController(controller);
    }

}
