package com.example.gogowaze.signalisation;

import com.fasterxml.jackson.databind.JsonNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class SignalData implements Serializable {

    private JSONObject mJsonObject;

    public SignalData(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public String getTitre() throws JSONException{
        return mJsonObject.getString("Titre");
    }

    public String getDescription() throws JSONException{
        return mJsonObject.getString("Description");
    }


    public String getGravite() throws JSONException {
        return mJsonObject.getString("Gravite");
    }

    public String getTypeAccident() throws JSONException {
        return mJsonObject.getString("Type");
    }

    public String toString() {
        return mJsonObject.toString();
    }

    public JSONObject getJson() {
        return this.mJsonObject;
    }

}
