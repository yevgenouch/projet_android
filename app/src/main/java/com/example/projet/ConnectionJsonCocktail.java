package com.example.projet;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ConnectionJsonCocktail extends AsyncTask<String, Void, List<Cocktail>> {

    // init var
    String requestMethod;
    JSONObject jsonObjectMessage;

    //init méthode + message
    public ConnectionJsonCocktail(String requestMethod_p, JSONObject jsonObjectMessage_p){
        this.requestMethod = requestMethod_p;
        this.jsonObjectMessage = jsonObjectMessage_p;
    }



    @Override
    protected List<Cocktail> doInBackground(String... urlTable) {

        // list + filtre data à recup
        try {
            List<Cocktail> cocktails = parse(get(urlTable[0],this.requestMethod,this.jsonObjectMessage));
            return cocktails;
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // échange connection
    public String get(String url, String pMethode, JSONObject pdt) throws IOException, JSONException {
        InputStream is = null;
        String parameters  = "r="+ URLEncoder.encode(pdt.toString(), "utf-8");
        // regarde méthode
        try {
            if(pMethode.equals("DELETE")){
                url+=pdt.getInt("id");
            }else if(pMethode.equals("GET")){
                url+="?"+parameters;
            }
            Log.v("R ", pdt.toString()+"\n");
            final HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod(pMethode);

            if(pMethode.equals("POST")||pMethode.equals("PUT")){
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(parameters);// here i sent the parameter
                out.close();
            }else{
                conn.setDoInput(true);
                conn.connect();
            }
            conn.connect();
            is = conn.getInputStream();

            return readIt(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Override
    protected void onPostExecute(List<Cocktail> cocktails) {
        super.onPostExecute(cocktails);
        // Log.e("Response", "" + server_response);
    }

    private List<Cocktail> parse(final String json) {
        try {
            // recup longueur élément
            final List cocktails = new ArrayList();
            final JSONArray jCocktailArray = new JSONArray(json);
            for (int i = 0; i < jCocktailArray.length(); i++) {
                cocktails.add(new Cocktail(jCocktailArray.optJSONObject(i)));
            }
            return cocktails;
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
