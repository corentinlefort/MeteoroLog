package com.main.mtorolog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Requester extends AppCompatActivity{

    private int resCode;
    private static String resultat;
    private Context callerContext;

    public Requester(Context context) {
        this.callerContext = context;
    }

    //execute une requete via Google Volley (passer en paramètres les champs requis pour la création d'une requête adaptée, reverse engineering pour voir lesquels)
    private String doRequest(final ArrayList params, final boolean favorite) {
        resCode = 0;
        String url = callerContext.getString(R.string.URLserveur);
        url += "?mode=" + params.get(0) + "&";
        for (int i = 1; i < params.size(); i++) {

            url += "id" + "=" + params.get(i) +"&";
            i++;
        }

        // Request a string response from the provided URL.
        RequestQueue queue = Volley.newRequestQueue(this.callerContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        resCode = 1;
                        Requester.resultat = response;
                        switch ((String) params.get(0)) {
                            case "getStationsList" :
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    ArrayList<Station> stations = new ArrayList<Station>();
                                    for (int i = 1;i <= jsonResponse.length();i++) {
                                        JSONObject cast = jsonResponse.getJSONObject(Integer.toString(i));
                                        Station s = new  Station(cast.getInt("rowid"),cast.getString("nom"),cast.getInt("fk_ville"),callerContext);
                                        stations.add(s);
                                    }
                                    MainActivity.updateStationList(stations);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } break;
                            case "getWeathersStation" :
                                try {
                                    JSONArray jsonResponse = new JSONArray(response);
                                    ArrayList weathers = new ArrayList();
                                    for (int i = 0;i < jsonResponse.length();i++) {
                                        JSONObject cast = jsonResponse.getJSONObject(i);
                                        weathers.add(cast);
                                    }
                                    if(favorite) {
                                        MainActivity.updateWeathersInfos(weathers, callerContext);
                                    } else {
                                        MeteoActivity.updateWeathersInfos(weathers, callerContext);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resCode = 0;
                new AlertDialog.Builder(callerContext)
                        .setTitle("Erreur réseau")
                        .setMessage("Une erreur est survenue. Vérifiez votre connexion internet ou contactez l'administrateur.")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        return Requester.resultat;
    }

    // récupérer la liste des stations
    public void getStationsList() {
        //faire requête XHR
        // Instantiate the RequestQueue.

        ArrayList parametresRequete = new ArrayList();
        parametresRequete.add("getStationsList");
        doRequest(parametresRequete, false);
    }

    //récupérer les données météo d'une station donnée
    public void getWeathersStation(int currentStation) {
        ArrayList parametresRequete = new ArrayList();
        parametresRequete.add("getWeathersStation");
        parametresRequete.add(currentStation);
        doRequest(parametresRequete, false);
    }

    //récupérer les données météo de la station préférée
    public void getWeatherPref(int station) {
        ArrayList parametresRequete = new ArrayList();
        parametresRequete.add("getWeathersStation");
        parametresRequete.add(station);
        doRequest(parametresRequete, true);
    }

    //permet de récupérer les données d'une ville (obsolète)
    @Deprecated
    public String getCityData(int paramVille) {
        ArrayList parametresRequete = new ArrayList();
        parametresRequete.add("getCityData");
        parametresRequete.add(paramVille);
        return doRequest(parametresRequete, false);
    }
}
