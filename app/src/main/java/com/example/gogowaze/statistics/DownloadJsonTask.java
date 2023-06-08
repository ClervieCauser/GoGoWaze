package com.example.gogowaze.statistics;

import android.os.AsyncTask;

import com.example.gogowaze.OnDataLoadedListener;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownloadJsonTask extends AsyncTask<String, Void, JSONObject> {
    private OnDataLoadedListener onDataLoadedListener;
    private ObjectMapper objectMapper;

    public DownloadJsonTask(OnDataLoadedListener onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
        this.objectMapper = new ObjectMapper(); // Initialisation de Jackson ObjectMapper
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return null;
        }
    }

    private JSONObject downloadUrl(String myurl) throws IOException {
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

            String jsonStr = sb.toString();

            // Convertit la chaîne JSON en un JsonNode avec Jackson
            JsonNode jsonNode = objectMapper.readTree(jsonStr);

            // Convertit le JsonNode en JSONObject pour le traitement ultérieur

            //itemList = mapper.readValue(jsonStr, mapper.getTypeFactory().constructCollectionType(List.class, clazz));

            JSONObject json = new JSONObject(jsonNode.toString());

            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (result == null) {
            return;
        }

        if (onDataLoadedListener != null) {
            onDataLoadedListener.onDataLoaded(result);
        }
    }
}