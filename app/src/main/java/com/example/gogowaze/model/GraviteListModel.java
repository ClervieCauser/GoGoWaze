package com.example.gogowaze.model;

import com.example.gogowaze.statistics.creerStat.IController;
import com.example.gogowaze.statistics.creerStat.IModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GraviteListModel extends Observable implements IModel {
    private List<GraviteModel> graviteList;
    private IController IController;

    public GraviteListModel(){
        super();
        graviteList = new ArrayList<>();
    }

    public void setController(IController IController) {
        this.IController = IController;
    }

    @Override
    public Object get(int position) {
        return graviteList.get(position);
    }

    @Override
    public int size() {
        return graviteList.size();
    }

    public void add(String city, int accidentsEleves, int accidentsFaibles, int accidentsModeres) {
        GraviteModel graviteModel = new GraviteModel(city, accidentsEleves, accidentsFaibles, accidentsModeres);
        graviteList.add(graviteModel);
        //IController.checkLabelColor();
        setChanged();
        notifyObservers();
    }

    @Override
    public void remove(int index) {
        if (index < graviteList.size()) {
            graviteList.remove(index);
            //IController.checkLabelColor();
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder("GraviteListModel{");
        graviteList.forEach(graviteModel -> stringBuilder.append(graviteModel.getVille()+", "));
        return "GraviteListModel{" + stringBuilder +"}";
    }
}
