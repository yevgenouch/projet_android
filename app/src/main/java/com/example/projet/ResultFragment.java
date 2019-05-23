package com.example.projet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ResultFragment extends Fragment  {
    private ConnectionJson connectionJSon;
    private TextView nomAlcool;
    private TextView typeAlcool;
    private View view;
    private List<Alcool> alcools = new ArrayList<>();
    private CocktailAdapter cocktailAdapter;
    private List<Cocktail> cocktails = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // initialisation de la liste cocktail + activité si click
        ListView listView = view.findViewById(R.id.cocktails_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getIntent().putExtra("cocktail",cocktails.get(position).toJson().toString());
                getFragmentManager().beginTransaction().replace(R.id.fragement_container, new CocktailDetailsFragment()).commit();

            }
        });

        // si le scan reçoit valeur
        if (HomeFragment.scan == true)
        {
            // effectuer connection bdd alcool & cocktail
            connectionJSon = new ConnectionJson();
            try{
                alcools = connectionJSon.execute("http://android.ega.tf/gr3/alcool/").get();
                cocktails = new ConnectionJsonCocktail("GET",
                        new JSONObject().put("type", alcools.get(0).type())).execute("http://android.ega.tf/gr3/cocktail/").get();
                cocktailAdapter = new CocktailAdapter(getContext(), cocktails);
                listView.setAdapter(cocktailAdapter);
            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (ExecutionException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }
            // nombre ligne avec alcool => toujours à 1 ligne
            for (int i=0; i<alcools.size();i++)
            {
                Log.v("Alcool", alcools.get(i).Nom());

            }

            nomAlcool = view.findViewById(R.id.nom_alcool);
            nomAlcool.setText(alcools.get(0).Nom());

            typeAlcool = view.findViewById(R.id.type_alcool);
            typeAlcool.setText(alcools.get(0).type());
        }
        return view;

    }

}
