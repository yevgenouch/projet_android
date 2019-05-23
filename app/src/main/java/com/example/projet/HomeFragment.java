package com.example.projet;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
//import com.google.gson.Gson;


public class HomeFragment extends Fragment {
    private String toast;

    boolean find = false;
    public static String result_CdeBarre;
    private View view;
    public static boolean scan = false;


    public HomeFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        displayToast();
    }
    // bouton scan et ajouter sur page home

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button scan = (Button) view.findViewById(R.id.btn_scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanFromFragment();
            }
        });
        Button add = (Button) view.findViewById(R.id.ajout_cocktail);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //Lors ce qu'on clic sur ajouter, on est redirigé vers le fragment d'ajout cocktail
                getFragmentManager().beginTransaction().replace(R.id.fragement_container, new AddCocktailFragment()).commit();
            }
        }
        );
        return view;
    }

    // lancement de la caméra du tel
    public void scanFromFragment() {
        IntentIntegrator.forSupportFragment(this).initiateScan();
        IntentIntegrator.forSupportFragment(this).setCaptureActivity(AnyOrientationCaptureActivity.class);
        IntentIntegrator.forSupportFragment(this).setOrientationLocked(true);
    }
    // alerte non utiliser
    private void displayToast() {
        if(getActivity() != null && toast != null) {
            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            toast = null;
        }
    }

    // récup du code barre + si null rien sinon stocker dans var + page résultat
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                toast = "Cancelled from fragment";
            } else {
                toast = "Scanned from fragment: " + result.getContents();
                result_CdeBarre = result.getContents();
                Log.v("Resultat scan", result_CdeBarre);

                scan = true;
                //dès qu'on a scanne le produit, on est redirigé vers le fragment resultat
                getFragmentManager().beginTransaction().replace(R.id.fragement_container, new ResultFragment()).commit();

            }

            // At this point we may or may not have a reference to the activity
            //displayToast();
        }
    }

    // mode portrait (caméra)
    public class AnyOrientationCaptureActivity extends CaptureActivity {

    }


}

