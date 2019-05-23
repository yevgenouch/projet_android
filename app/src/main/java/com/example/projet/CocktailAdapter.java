package com.example.projet;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class CocktailAdapter extends ArrayAdapter<Cocktail> {
    // init list
    private List<Cocktail> cocktails;

    public CocktailAdapter(Context context, List<Cocktail> cocktails) {
        // adapt list par bdd
        super(context, 0, cocktails);
        this.cocktails = cocktails;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // recup data item à cette position

        Cocktail cocktail = getItem(position);

        // verifier si view existe déjà sinon augmenter view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cocktail, parent, false);
        }

        // convertir view
        TextView name = convertView.findViewById(R.id.list_cocktail_name);

        // garder valeur du texte ecrit
        name.setText(cocktail.getName());

        // retourner view complet à rendre sur l'écran
        return convertView;

    }

}