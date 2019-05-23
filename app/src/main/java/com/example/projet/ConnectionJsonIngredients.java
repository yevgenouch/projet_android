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
// voir comment ConnectionJsonCocktail

public class ConnectionJsonIngredients extends AsyncTask<String, Void, List<Ingredient>> {

    String requestMethod;
    JSONObject jsonObjectMessage;

    //init méthode + message
    public ConnectionJsonIngredients(String requestMethod_p, JSONObject jsonObjectMessage_p){
        this.requestMethod = requestMethod_p;
        this.jsonObjectMessage = jsonObjectMessage_p;
    }



    @Override
    protected List<Ingredient> doInBackground(String... urlTable) {

        try {
            List<Ingredient> ingredients = parse(get(urlTable[0],this.requestMethod,this.jsonObjectMessage));
            return ingredients;
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String get(String url, String pMethode, JSONObject pdt) throws IOException, JSONException {
        InputStream is = null;
        String parameters  = "r="+ URLEncoder.encode(pdt.toString(), "utf-8");
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
    protected void onPostExecute(List<Ingredient> ingredients) {
        super.onPostExecute(ingredients);
        // Log.e("Response", "" + server_response);
    }

    private List<Ingredient> parse(final String json) {
        try {
            final List ingredients = new ArrayList();
            final JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                ingredients.add(new Ingredient(jsonArray.optJSONObject(i)));
            }
            return ingredients;
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
