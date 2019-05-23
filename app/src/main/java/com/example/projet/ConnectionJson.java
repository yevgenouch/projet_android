package com.example.projet;

import android.content.ContentValues;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ConnectionJson extends AsyncTask<String, Void, List<Alcool>> {
    String server_response;

    public ConnectionJson(){
    }

    @Override
    protected List<Alcool> doInBackground(String... strings) {

        try {
            JSONObject json = new JSONObject();
            // recup du code barre
            json.put("Cde_Barre", HomeFragment.result_CdeBarre);
            //test
            //json.put("Cde_Barre", "3760093622349");
            //json.put("CdeBarre", HomeFragment.result_CdeBarre);

            //mettre en json
            Log.v("Json", String.valueOf(json));
            // recup tout cocktail ayant le même alcool
            List<Alcool> listAlcool = parse(get(strings[0], json));
            for (int i = 0; i < listAlcool.size(); i++) {
                Log.v("ALCOOL ", listAlcool.get(i).id()+ " " + listAlcool.get(i).Nom() + " " + listAlcool.get(i).type() + " "+listAlcool.get(i).Cde_Barre()+ "\n");
            }
            return listAlcool;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Alcool> alcools) {
        super.onPostExecute(alcools);
        Log.e("Response", "" + server_response);
    }

    // connection bdd

    public String get(String url, JSONObject pdt) throws IOException {
        InputStream is = null;
        try {
            final HttpURLConnection conn = (HttpURLConnection) new URL(url + "?r=" + URLEncoder.encode(pdt.toString(), "utf-8")).openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // démarre la connexion
            conn.connect();
            is = conn.getInputStream();
            // Lit le InputStream et le sauve dans une chaîne
            return readIt(is);
        } finally {
            // S'assure que le InputStream est fermé après l’arrêt de l'application
            if (is != null) {
                is.close();
            }
        }
    }

    private List<Alcool> parse(final String json) {
        try {
            final List<Alcool> alcools = new ArrayList();
            final JSONArray jAlcoolArray = new JSONArray(json);
            for (int i = 0; i < jAlcoolArray.length(); i++) {
                alcools.add(new Alcool(jAlcoolArray.optJSONObject(i)));
            }
            return alcools;
        } catch (JSONException e) {
            Log.v(ContentValues.TAG, "[JSONException] e : " + e.getMessage());
        }
        return null;
    }

    private String readIt(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            response.append(line).append('\n');
        }
        return response.toString();
    }

}