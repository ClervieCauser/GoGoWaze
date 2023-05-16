package com.example.gogowaze;

import android.os.AsyncTask;

import com.example.gogowaze.AccidentData;
import com.example.gogowaze.OnDataLoadedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadJsonTask extends AsyncTask<String, Void, AccidentData> {
    private OnDataLoadedListener onDataLoadedListener;

    public DownloadJsonTask(OnDataLoadedListener onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
    }

    @Override
    protected AccidentData doInBackground(String... urls) {
        try {
            String action = urls[1]; // Récupérer l'action spécifiée

            if (action.equals("update")) {
                // Effectuer la requête de mise à jour
                String updatedAccidentJson = "{\"Nice\":{\"gravite\":{\"faible\":100,\"modere\":50,\"elevee\":20},\"type_accident\":{\"voiture\":120,\"moto\":60,\"camion\":10,\"velo\":30,\"pieton\":40}}"; // Exemple de données mises à jour au format JSON
                return updateUrl(urls[0], updatedAccidentJson);
            }
            else if (action.equals("delete")) {
                // Effectuer la requête de suppression
                return deleteUrl(urls[0]);
            }
            else if (action.equals("download")){
                //recuperer les donnees normal
                return downloadUrl(urls[0]);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private AccidentData downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            JSONObject json = new JSONObject(contentAsString);

            return new AccidentData(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private AccidentData updateUrl(String myurl, String data) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT"); // Utilisation de la méthode PUT pour la mise à jour des données
            conn.setDoInput(true);
            conn.setDoOutput(true); // Permet l'envoi de données dans le corps de la requête
            conn.setRequestProperty("Content-Type", "application/json"); // Spécifie le type de contenu JSON
            conn.connect();

            // Envoyer les données mises à jour
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convertir le flux d'entrée en chaîne
            String contentAsString = readIt(is);
            JSONObject json = new JSONObject(contentAsString);

            return new AccidentData(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private AccidentData deleteUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("DELETE"); // Utilisation de la méthode DELETE pour la suppression de données
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convertir le flux d'entrée en chaîne
            String contentAsString = readIt(is);
            JSONObject json = new JSONObject(contentAsString);

            return new AccidentData(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    public String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        reader.close();
        return sb.toString();
    }

    @Override
    protected void onPostExecute(AccidentData result) {
        if (result == null) {
            return;
        }

        if (onDataLoadedListener != null) {
            onDataLoadedListener.onDataLoaded(result);
        }
    }
}
