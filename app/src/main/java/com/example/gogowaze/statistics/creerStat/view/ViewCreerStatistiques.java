package com.example.gogowaze.statistics.creerStat.view;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gogowaze.R;
import com.example.gogowaze.statistics.creerStat.CreerStatAdapter;
import com.example.gogowaze.statistics.creerStat.IController;
import com.example.gogowaze.statistics.creerStat.IView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ViewCreerStatistiques implements IView, Observer {

    private CreerStatAdapter adapter;
    private final ViewGroup layout;
    private  boolean modelIsReady = false;
    private final List<TextView> itemList = new ArrayList<>();
    private IController iController;

    public ViewCreerStatistiques(ViewGroup layout) {
        this.layout = layout;
        Log.d(TAG, "View is created");
        layout.findViewById(R.id.buttonCreate).setOnClickListener(click ->  iController.onCreateStatClicked());  // userAction
        //layout.findViewById(R.id.addTeam2).setOnClickListener( click ->  IController.userActionAddClickTeam2());  // userAction
    }

    public void setAdapter(CreerStatAdapter adapter) {
        this.adapter = adapter;
        modelIsReady = true;
       ((ListView)layout.findViewById(R.id.listViewStatistics)).setAdapter(adapter);
        adapter.refresh();
        Log.d(TAG, "model Is Ready ");
    }

    public void setController(IController IController) {
        this.iController = IController;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(modelIsReady) {
            itemList.clear();           //not very performent... :/
            adapter.refresh();          //add all view
            Log.d(TAG, "View updated: "+ itemList );
        }

    }

    @Override
    public TextView getLabelTeam1() {
        return layout.findViewById(R.id.editTextAccidentsFaibles);
    }

    @Override
    public TextView getLabelTeam2() {
        return layout.findViewById(R.id.editTextAccidentsModere);

    }

    @Override
    public TextView getLabelTeam3() {
        return layout.findViewById(R.id.editTextAccidentsEleves);

    }

    @Override
    public EditText getInputName() {
        return layout.findViewById(R.id.editTextCity);
    }

    @Override
    public void addItem(TextView textView) {

    }
}
