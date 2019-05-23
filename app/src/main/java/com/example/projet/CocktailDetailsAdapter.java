package com.example.projet;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class CocktailDetailsAdapter extends ArrayAdapter<Ingredient> {

    private List<Ingredient> ingredients;

    public CocktailDetailsAdapter(Context context, List<Ingredient> ingredients) {

        super(context, 0, ingredients);
        this.ingredients = ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Ingredient ingredient = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the vie
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cocktail_detail, parent, false);
        }

        // Lookup view for data population
        TextView name = convertView.findViewById(R.id.detail_ingredient_name);
        TextView quantity = convertView.findViewById(R.id.detail_ingredient_quantity);

        // Populate the data into the template view using the data object
        name.setText(ingredient.getName());
        quantity.setText(ingredient.getQuantity());

        // Return the completed view to render on screen
        return convertView;
    }

}