package com.example.projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    private List<Ingredient> ingredients = new ArrayList<>();

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {

        super(context, 0, ingredients);

        this.ingredients = ingredients;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        final Ingredient ingredient = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient, parent, false);

        }

        // Lookup view for data population

        final TextView name = convertView.findViewById(R.id.ingredient_name);
        final TextView quantity = convertView.findViewById(R.id.ingredient_quantity);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ingredient.setName(name.getText().toString());
            }
        });

        quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ingredient.setQuantity(quantity.getText().toString());
            }
        });

        // Populate the data into the template view using the data object

        name.setText(ingredient.getName());
        quantity.setText(ingredient.getQuantity());

        // Return the completed view to render on screen

        return convertView;

    }
    public void add(Ingredient message) {
        this.ingredients.add(message);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Ingredient getItem(int i) {
        return ingredients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
