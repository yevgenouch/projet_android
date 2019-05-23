package com.example.projet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CocktailDetailsFragment extends Fragment {

    // init var
    private Cocktail cocktail;
    private TextView cocktailName;
    private CocktailDetailsAdapter adapter;

    public CocktailDetailsFragment(){

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // connection à la bdd cocktail
        // comparaison id bdd cocktail  à l'id_cocktail bdd ingredient
        try {
            ListView listView = getView().findViewById(R.id.details_list);
            cocktail = new Cocktail(new JSONObject(getActivity().getIntent().getStringExtra("cocktail")));
            List<Ingredient> ingredientList = new ConnectionJsonIngredients("GET", new JSONObject()
                    .put("id",cocktail.getId())).execute("http://android.ega.tf/gr3/ingredients/").get();
            cocktail.setIngredients(ingredientList);
            cocktailName = getView().findViewById(R.id.cocktail_name_consult);
            cocktailName.setText(cocktail.getName());
            adapter = new CocktailDetailsAdapter(getContext(), cocktail.getIngredients());
            listView.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

    }

    //  recup le fragment pour add ligne ingrédient en fonction bdd
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cocktail_details, container, false);
    }

}
