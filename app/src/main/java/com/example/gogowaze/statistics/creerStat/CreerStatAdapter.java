package com.example.gogowaze.statistics.creerStat;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gogowaze.R;
import com.example.gogowaze.model.GraviteModel;

public class CreerStatAdapter extends BaseAdapter {


    private final LayoutInflater inflater;
    private final IModel IModel;
    private final IView view;
    private final IController IController;



    public CreerStatAdapter(Context context, IController IController, IModel IModel, IView view) {
        inflater = LayoutInflater.from(context);
        this.IController = IController;
        this.IModel = IModel;
        this.view = view;
    }

    public void refresh() {
        Log.d(TAG, "listView refreshed with ==> " + IModel);
        //todo check team==TEAM1
        notifyDataSetChanged();     //refresh display
    }


    @Override
    public int getCount() {
        return IModel.size();
    }

    @Override
    public Object getItem(int i) {
        return IModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ConstraintLayout layoutItem;

        //(1) : Réutilisation des layouts
        layoutItem = (ConstraintLayout) (view == null ? inflater.inflate(R.layout.view_gravite_adapter, parent, false) : view);

        //(2) : Récupération des TextView de notre layout
        TextView ville = layoutItem.findViewById(R.id.ville);
        TextView graviteElevee = layoutItem.findViewById(R.id.graviteElevee);
        TextView graviteMoyenne = layoutItem.findViewById(R.id.graviteMoyenne);
        TextView graviteFaible = layoutItem.findViewById(R.id.graviteFaible);

        this.view.addItem(ville);
        this.view.addItem(graviteElevee);

        this.view.addItem(graviteMoyenne);
        this.view.addItem(graviteFaible);

        //(3) : Renseignement des valeurs
        GraviteModel model = (GraviteModel) IModel.get(position);
        ville.setText(model.getVille());
        graviteElevee.setText("Elevées : "+String.valueOf(model.getAccidentsEleves()));
        graviteMoyenne.setText("Moyens : "+String.valueOf(model.getAccidentsModeres()));
        graviteFaible.setText("Faibles : "+String.valueOf(model.getAccidentsFaibles()));




        //écouter si clic sur la vue
        layoutItem.setOnClickListener( clic ->  IController.onClickItem(position) );
        //On retourne l'item créé.
        return layoutItem;
    }
}
