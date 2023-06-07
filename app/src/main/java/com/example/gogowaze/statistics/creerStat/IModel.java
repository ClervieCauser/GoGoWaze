package com.example.gogowaze.statistics.creerStat;

import com.example.gogowaze.model.GraviteModel;

public interface IModel {

    void add(String city, int accidentsEleves, int accidentsFaibles, int accidentsModeres);
    void remove(int index);
    int size();
    Object get(int position);
}



