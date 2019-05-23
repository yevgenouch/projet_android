package com.example.projet;

import org.json.JSONObject;

public class Alcool {
    // init var
    private final long id;
    private final long Cde_Barre;
    private final String type;
    private final String Nom;

    public Alcool(JSONObject jObject) {
        this.id = jObject.optLong("ID");
        this.Cde_Barre = jObject.optLong("Cde_Barre");
        this.type = jObject.optString("Type");
        this.Nom = jObject.optString("Nom");
    }

    public long id() { return id; }
    public long Cde_Barre() { return Cde_Barre; }
    public String type() { return type; }
    public String Nom() { return Nom; }
}
