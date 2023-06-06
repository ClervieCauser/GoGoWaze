package com.example.gogowaze.statistics.creerStat;

import android.widget.EditText;
import android.widget.TextView;

public interface IView {
    TextView getLabelTeam1();
    TextView getLabelTeam2();

    TextView getLabelTeam3();
    EditText getInputName();

    void addItem(TextView textView);
}
