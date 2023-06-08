package com.example.gogowaze.signalisation;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadJsonTaskSignal extends AsyncTask<String, Void, SignalData> {
    private OnDataLoadedListenerSignal onDataLoadedListenerSignal;
    private ObjectMapper objectMapper;

    public DownloadJsonTaskSignal(OnDataLoadedListenerSignal onDataLoadedListenerSignal) {
        this.onDataLoadedListenerSignal = onDataLoadedListenerSignal;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected SignalData doInBackground(String... params) {
        try {
            String url = params[0];
            String action = params[1];
            String data = params[2];

            if (action.equals("update")) {
                return updateUrl(url, data);
            } else if (action.equals("delete")) {
                return deleteUrl(url);
            }
            return downloadUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private SignalData downloadUrl(String myurl) throws IOException {
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
            JSONObject json = new JSONObject(jsonNode.toString());

            return new SignalData(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    private SignalData updateUrl(String url, String data) throws IOException {
        HttpURLConnection conn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            URL updateUrl = new URL(url);
            conn = (HttpURLConnection) updateUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Envoi des données JSON mises à jour au serveur
            outputStream = conn.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lecture de la réponse du serveur
                inputStream = conn.getInputStream();
                String response = convertInputStreamToString(inputStream);

                // Traitement de la réponse (si nécessaire)
                JSONObject jsonResponse = new JSONObject(response);
                return new SignalData(jsonResponse);
            } else {
                // Gérer les erreurs de la requête
                throw new IOException("Error: " + responseCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private SignalData deleteUrl(String url) throws IOException {
        HttpURLConnection conn = null;
        InputStream inputStream = null;

        try {
            URL deleteUrl = new URL(url);
            conn = (HttpURLConnection) deleteUrl.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setDoInput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lecture de la réponse du serveur
                inputStream = conn.getInputStream();
                String response = convertInputStreamToString(inputStream);

                // Traitement de la réponse (si nécessaire)
                JSONObject jsonResponse = new JSONObject(response);
                return new SignalData(jsonResponse);
            } else {
                // Gérer les erreurs de la requête
                throw new IOException("Error: " + responseCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }

        reader.close();
        return sb.toString();
    }

    @Override
    protected void onPostExecute(SignalData result) {
        if (result != null) {
            onDataLoadedListenerSignal.onDataLoaded(result);
        }
    }
}
