package com.example.projet;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient {

    // init var
    private int cocktailID;
    private String name;
    private String quantity;

    // recup élément bdd ingredient
    public Ingredient(JSONObject jObject) {
        this.cocktailID = jObject.optInt("cocktailID");
        this.name = jObject.optString("name");
        this.quantity = jObject.optString("quantity");
    }

    public Ingredient(String name, String quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", this.name);
            jsonObject.put("quantity", this.quantity);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getCocktailID() {
        return cocktailID;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }
}
