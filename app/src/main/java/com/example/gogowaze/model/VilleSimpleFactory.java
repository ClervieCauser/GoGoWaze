package com.example.gogowaze.model;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class VilleSimpleFactory implements VilleInterface {
    public static VilleInterface createVille(JSONObject villeData, String villeNameParam) {
        try {
            String villeName = villeNameParam; // À ajuster en fonction de la structure de votre JSON

            JSONObject graviteJson = villeData.getJSONObject("gravite");
            JSONObject typeAccidentJson = villeData.getJSONObject("type_accident");

            Map<String, Integer> gravite = new HashMap<>();
            Map<String, Integer> typeAccident = new HashMap<>();

            // Remplir les maps à partir des objets JSON
            Iterator<String> graviteKeys = graviteJson.keys();
            while (graviteKeys.hasNext()) {
                String key = graviteKeys.next();
                int value = graviteJson.getInt(key);
                gravite.put(key, value);
            }

            Iterator<String> typeAccidentKeys = typeAccidentJson.keys();
            while (typeAccidentKeys.hasNext()) {
                String key = typeAccidentKeys.next();
                int value = typeAccidentJson.getInt(key);
                typeAccident.put(key, value);
            }

            // Créer un nouvel objet VilleImpl
            return new VilleImpl(villeName, gravite, typeAccident);

        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid JSON format", e);
        }
    }
}
