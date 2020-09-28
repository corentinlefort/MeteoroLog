package com.main.mtorolog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Station> allStations = new ArrayList<Station>();
    private static int currentStation = 0;
    public static ListView listViewStation;
    public static Context context;
    public static ArrayList weathers;
    public static TextView meteo_name;
    public static TextView temperature;
    public static ImageView meteo_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        meteo_name = findViewById(R.id.meteo_name);
        temperature = findViewById(R.id.temperature_accueil);
        meteo_image = findViewById(R.id.meteo_image);
        this.setupActionBar(getSupportActionBar());
        this.setupListStation((ListView)findViewById(R.id.listStations));
        this.setNamePref();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * permet la mise en place de l'action bar dans l'activité
     * @param ab
     */
    public void setupActionBar(ActionBar ab) {
        ab.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDark));
        ab.setDisplayShowHomeEnabled(true);
        ab.setLogo(R.drawable.logo);
        ab.setDisplayUseLogoEnabled(true);
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //initialise la liste des stations
    private void setupListStation(final ListView listView) {
        this.getStationsFromServer();
        listView.setBackground(getResources().getDrawable(R.color.colorPrimary));
        listView.setDividerHeight(15);
        ArrayAdapter<Station> adapterStations = new ArrayAdapter<Station>(this,
                android.R.layout.activity_list_item, android.R.id.text1, this.allStations){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tv.setTextSize(25);
                tv.setTypeface(null, Typeface.BOLD);
                return view;
            }
        };

        listView.setAdapter(adapterStations);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Ici on detecte le clic sur un élément de la liste et on toast son id

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Station station = (Station)listView.getItemAtPosition(position);

                MainActivity.currentStation = station.getId();
                Intent intent = new Intent(getBaseContext(),MeteoActivity.class);
                startActivity(intent);
            }

        });
        this.listViewStation = listView;

    }

    //appelle Requester afin de récupérer les stations
    private void getStationsFromServer() {
        Requester r = new Requester(this);
        r.getStationsList();
    }

    //met à jour la liste des stations
    public static void updateStationList(ArrayList<Station> prmArrayList) throws JSONException {
        allStations.clear();
        for(int i = 0; i < prmArrayList.size(); i++) {
            allStations.add(prmArrayList.get(i));
        }
        MainActivity.listViewStation.invalidateViews();
        MainActivity.updatePreferredStationField(context);

    }

    public static int getCurrentStation() {
        return currentStation;
    }

    //permet l'affichage d'un message personnalisé à l'ouverture de l'application
    public static void setNamePref() {
        String nom = "";
        TextView welcome = ((Activity) context).findViewById(R.id.welcomeMessage);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                context
        );
        if(sharedPreferences.contains("prenom")) {
            nom = sharedPreferences.getString("prenom", "");
        }
        if(nom == "") {
            welcome.setText(context.getResources().getString(R.string.default_welcome));
        } else {
            welcome.setText(context.getResources().getString(R.string.welcome)+ " " + nom);
        }
    }

    //met à jour les données météo de la station préférée
    public static void updateWeathersInfos(ArrayList weathersList, Context context) throws JSONException {
        weathers = weathersList;
        Date currentTime = Calendar.getInstance().getTime();
        int currentHour = currentTime.getHours(); // récupère l'heure
        JSONObject weather = null;
        if (currentHour >= 6 && currentHour < 12) {
            //matin
            weather = (JSONObject) weathers.get(0);
        }
        else if (currentHour >= 12 && currentHour < 19) {
            //aprem
            weather = (JSONObject) weathers.get(1);

        }
        else if (currentHour >= 19 && currentHour < 23) {
            //soirée
            weather = (JSONObject) weathers.get(2);

        }
        else if (currentHour >= 23 && currentHour < 6) {
            //nuit
            weather = (JSONObject) weathers.get(3);
        }

        Drawable myDrawable;
        switch (weather.getInt("fk_meteo_type")) {
            case 1 :
                myDrawable = context.getResources().getDrawable(R.drawable.w1);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 2 :
                myDrawable = context.getResources().getDrawable(R.drawable.w2);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 3 :
                myDrawable = context.getResources().getDrawable(R.drawable.w3);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 4 :
                myDrawable = context.getResources().getDrawable(R.drawable.w4);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 5 :
                myDrawable = context.getResources().getDrawable(R.drawable.w5);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 6 :
                myDrawable = context.getResources().getDrawable(R.drawable.w6);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 7 :
                myDrawable = context.getResources().getDrawable(R.drawable.w7);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 8 :
                myDrawable = context.getResources().getDrawable(R.drawable.w8);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 9 :
                myDrawable = context.getResources().getDrawable(R.drawable.w9);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 10 :
                myDrawable = context.getResources().getDrawable(R.drawable.w10);
                meteo_image.setImageDrawable(myDrawable);
                break;
            case 11 :
                myDrawable = context.getResources().getDrawable(R.drawable.w11);
                meteo_image.setImageDrawable(myDrawable);
                break;
        }

        temperature.setText(weather.getString("temperature")+"°C");
        meteo_name.setText((String) weather.getString("meteo_name"));
    }

    //met à jour le champ comportant le nom de la station préférée
    public static void updatePreferredStationField(Context context) throws JSONException {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                context
        );
        TextView tv = (TextView)  ((Activity) context).findViewById(R.id.stationPrefName);
        if(sharedPreferences.contains("station")) {
            int id = Integer.valueOf(sharedPreferences.getString("station",
                    "-1"));

            tv.setTypeface(null, Typeface.BOLD);
            if (id != -1) {
                Requester r = new Requester(context);
                r.getWeatherPref(id);
                tv.setText(allStations.get(id-1).getNomStation());
                //il faut définir dans quelle période de la journée nous sommes


            }



        }else {
            tv.setText(context.getResources().getString(R.string.gopref));
        }
    }

}
