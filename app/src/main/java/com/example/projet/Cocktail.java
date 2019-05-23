package com.example.projet;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cocktail {

    // init var
    private int id;
    private String name;
    private String type;
    private List<Ingredient> ingredients;


    public Cocktail() {
        // recup list ingredient du cocktail dans bdd ingredient
        this.ingredients = new ArrayList<>();
    }

    public Cocktail(JSONObject jObject) {
        // var cocktail com bdd cocktail + recup ingredients de bdd ingredient
        this.id = jObject.optInt("id");
        this.name = jObject.optString("name");
        this.type = jObject.optString("type");
        this.ingredients = new ArrayList<>();
        final JSONArray jIngredientsArray = jObject.optJSONArray("ingredients");
        if (jIngredientsArray != null)
            for (int i = 0; i < jIngredientsArray.length(); i++) {
                ingredients.add(new Ingredient(jIngredientsArray.optJSONObject(i)));
            }
    }

    public void addIngredient(Ingredient ingredient) {
        // add ingredient
        this.ingredients.add(ingredient);
    }

    // recup id, name, type
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    // recup ingredient de list
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    //
    public JSONObject toJson() {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        try {
            return new JSONObject(gson.toJson(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }
}
