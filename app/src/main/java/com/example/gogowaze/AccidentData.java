package com.example.gogowaze;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccidentData implements Serializable {

    private JSONObject mJsonObject;

    public AccidentData(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public int getGravite(String ville, String gravite) throws JSONException {
        JSONObject villeObject = mJsonObject.getJSONObject(ville);
        JSONObject graviteObject = villeObject.getJSONObject("gravite");
        return graviteObject.getInt(gravite);
    }

    public int getTypeAccident(String ville, String type) throws JSONException {
        JSONObject villeObject = mJsonObject.getJSONObject(ville);
        JSONObject typeObject = villeObject.getJSONObject("type_accident");
        return typeObject.getInt(type);
    }

    public JSONObject getTypeAccidentsForCity(String city) throws JSONException {
        JSONObject cityObject = mJsonObject.getJSONObject(city);
        return cityObject.getJSONObject("type_accident");
    }

    public List<Accident> getTypeAccidentsListForCity(String city) throws JSONException {
        List<Accident> accidentList = new ArrayList<>();

        JSONObject cityObject = mJsonObject.getJSONObject(city);
        JSONObject typeAccidentsObject = cityObject.getJSONObject("type_accident");
        JSONArray names = typeAccidentsObject.names();

        if (names != null) {
            for (int i = 0; i < names.length(); i++) {
                String type = names.getString(i);
                int count = typeAccidentsObject.getInt(type);
                accidentList.add(new Accident(type, count));
            }
        }

        return accidentList;
    }

   public String toString() {
        return mJsonObject.toString();
    }
}
