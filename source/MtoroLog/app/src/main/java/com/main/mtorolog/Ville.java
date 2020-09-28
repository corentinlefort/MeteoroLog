package com.main.mtorolog;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class Ville {
    private String name;
    private int id;
    private int idPays;

    @Deprecated
    public Ville(int i, Context context) {
        /*Requester r = new Requester(context);

        String ville_json = r.getCityData(i);
        try {
            JSONObject jObj= new JSONObject(ville_json);
            this.name = jObj.get("nom").toString();
            this.id = Integer.parseInt(jObj.get("rowid").toString());
            this.idPays = Integer.parseInt(jObj.get("fk_pays").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getIdPays() {
        return this.idPays;
    }
}
