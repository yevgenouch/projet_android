package com.example.projet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddCocktailFragment extends Fragment {

    // init var
    private Button createButton;
    private ImageButton addButton;
    private Cocktail cocktail = new Cocktail();
    private List<Ingredient> ingredients = new ArrayList<>();
    private EditText cocktailName;
    private Spinner cocktailType;
    private IngredientAdapter ingredientAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // recup data dans var
        createButton = getView().findViewById(R.id.create_cocktail_button);
        addButton = getView().findViewById(R.id.add_ingredient_button);
        cocktailName = getView().findViewById(R.id.cocktail_name);
        cocktailType = getView().findViewById(R.id.cocktail_type_spinner);

        // init list
        final ListView listView = getView().findViewById(R.id.ingredients_list);
        // init cocktail dépendent ingredient
        cocktail.addIngredient(new Ingredient("",""));
        ingredients.add(new Ingredient("",""));

        // add pls ingredient
        ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
        listView.setAdapter(ingredientAdapter);

        // click bouton + add ligne ingredient à remplir
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientAdapter.add(new Ingredient("",""));
            }
        });

        // click bouton valider + envoie à la bdd cocktail & ingredient
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String json = new Gson().toJson(ingredients);
                    String type = cocktailType.getSelectedItem().toString();
                    String name = cocktailName.getText().toString();
                    new ConnectionJsonCocktail("POST", new JSONObject().put("type",type)
                            .put("name", name).put("ingredients", new JSONArray(json))).execute("http://android.ega.tf/gr3/cocktail/");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // appele xml fragment pour add ligne ingredient (visu)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_cocktail, container, false);
    }
}
