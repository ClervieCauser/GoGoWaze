package com.example.gogowaze.model;

import com.example.gogowaze.statistics.creerStat.IController;
import com.example.gogowaze.statistics.creerStat.IModel;

import java.util.Observable;

public class GraviteModel {
    private String ville;
    private int accidentsEleves;
    private int accidentsFaibles;
    private int accidentsModeres;

    private IController IController;

    public GraviteModel(String ville, int accidentsEleves, int accidentsFaibles, int accidentsModeres) {
        this.ville = ville;
        this.accidentsEleves = accidentsEleves;
        this.accidentsFaibles = accidentsFaibles;
        this.accidentsModeres = accidentsModeres;
    }

    //observable part

    public void setController(IController IController) {
        this.IController = IController;
        //IController.checkLabelColor();
    }

    // getters
    public String getVille() {
        return ville;
    }

    public int getAccidentsEleves() {
        return accidentsEleves;
    }

    public int getAccidentsFaibles() {
        return accidentsFaibles;
    }

    public int getAccidentsModeres() {
        return accidentsModeres;
    }

    // setters
    public void setVille(String ville) {
        this.ville = ville;
        //setChanged();
        //notifyObservers();
    }

    public void setAccidentsEleves(int accidentsEleves) {
        this.accidentsEleves = accidentsEleves;
        //setChanged();
        //notifyObservers();
    }

    public void setAccidentsFaibles(int accidentsFaibles) {
        this.accidentsFaibles = accidentsFaibles;
        //setChanged();
        //notifyObservers();
    }

    public void setAccidentsModeres(int accidentsModeres) {
        this.accidentsModeres = accidentsModeres;
        //setChanged();
        //notifyObservers();
    }
}