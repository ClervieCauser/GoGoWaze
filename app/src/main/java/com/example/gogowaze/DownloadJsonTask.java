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
            return downloadUrl(urls[0]);
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
