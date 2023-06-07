package com.example.gogowaze.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Accident implements Serializable {
    public interface Observer {
        void onAccidentChanged(Accident accident);
    }

    private List<Observer> observers = new ArrayList<>();

    private String type;
    private int count;

    public Accident(String type, int count) {
        this.type = type;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyObservers();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyObservers();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.onAccidentChanged(this);
        }
    }
}
