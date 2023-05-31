package com.example.gogowaze.statistics.controller;

import android.content.Context;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.gogowaze.model.Accident;
import com.example.gogowaze.statistics.AccidentData;

import org.json.JSONException;

import java.util.List;

public class AccidentController implements Accident.Observer {
    private AccidentData accidentData;

    public AccidentController(AccidentData accidentData) {
        this.accidentData = accidentData;
    }

    public List<Accident> getTypeAccidentsListForCity(String city) throws JSONException {
        return accidentData.getTypeAccidentsListForCity(city);
    }

    public List<Accident> getGravityListForCity(String city) throws JSONException {
        return accidentData.getGravityListForCity(city);
    }

    @Override
    public void onAccidentChanged(Accident accident) {
        // Mettre à jour le modèle ou la vue en conséquence



    }

}
