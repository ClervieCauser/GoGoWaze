package com.example.gogowaze.model;

import java.util.Map;

public class VilleImpl implements VilleInterface {
    private String nom;
    private Map<String, Integer> gravite;
    private Map<String, Integer> typeAccident;

    public VilleImpl(String nom, Map<String, Integer> gravite, Map<String, Integer> typeAccident) {
        this.nom = nom;
        this.gravite = gravite;
        this.typeAccident = typeAccident;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public Map<String, Integer> getGravite() {
        return gravite;
    }

    @Override
    public Map<String, Integer> getTypeAccident() {
        return typeAccident;
    }
}

