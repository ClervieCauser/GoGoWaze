package com.example.gogowaze.statistics.creerStat.controller;

import android.util.Log;

import com.example.gogowaze.model.GraviteListModel;
import com.example.gogowaze.model.GraviteModel;
import com.example.gogowaze.statistics.creerStat.IController;
import com.example.gogowaze.statistics.creerStat.IModel;
import com.example.gogowaze.statistics.creerStat.IView;

public class ControllerGravite implements IController {
    private final String TAG = getClass().getSimpleName();
    private final IView view;
    private final IModel model;

    /**
     *  @param view the view to update
     *  @param model in MVC
     */
    public ControllerGravite(IView view, IModel model) {
        Log.d(TAG, "ControllerGravite is created" );
        this.view = view;
        this.model = model;
    }

    @Override
    public void onClickItem(int position) {

    }

    @Override
    public void onCreateStatClicked() {
        addGraviteToGraviteListModel( view.getInputName().getText().toString(),
                Integer.parseInt(view.getLabelTeam3().getText().toString()),
                Integer.parseInt(view.getLabelTeam1().getText().toString()),
                Integer.parseInt(view.getLabelTeam1().getText().toString()));
    }

    /**
     * update the model and update UI
     * @param ville input value
     */
    private void addGraviteToGraviteListModel(String ville, int accidentsEleves, int accidentsFaibles, int accidentsModeres ) {
        if ( !ville.equals("") ) {
            Log.d(TAG, "Updating gravite : "+ville+" "+accidentsEleves+" "+accidentsFaibles+" "+accidentsModeres+"\n");
            model.add(ville, accidentsEleves, accidentsFaibles, accidentsModeres);
            //(GraviteListModel)model.add(gravite);
            //((GraviteModel)model).setGravite(gravite);
        }
    }

    /**
     * callback from View
     * controller doesn't notify the view ! 'cause it's the model's responsibility !!!
     * @param position index in the list
     */
    @Override
    public void onListItemClicked(int position) {
        Log.d(TAG, "item clicked = " + position );
        if (model.size()>0) {
            model.remove(position);
            //checkLabelColor();
            if (model.size() == 0) {
                Log.d(TAG, "empty team");
            }
        }
    }
}