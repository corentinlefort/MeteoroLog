package com.main.mtorolog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MeteoActivity extends AppCompatActivity {

    public static ArrayList weathers;
    public static TextView cityName;
    public int selectedDate;
    public TextView humidite;
    public TextView meteoName;
    public TextView temperature;
    public ImageView imageMeteo;
    private Date currentTime = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);
        setupActionBar(getSupportActionBar());

        cityName = findViewById(R.id.cityName);
        humidite = findViewById(R.id.humidity);
        meteoName = findViewById(R.id.meteoName);
        temperature = findViewById(R.id.meteo_name);
        imageMeteo = findViewById(R.id.meteoImage);
        getWeatherInfo();
        Spinner dateSelector = findViewById(R.id.dateSelector);
        String[] options = new String[7];
        for (int i =0; i < 7; i++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE,i);
            SimpleDateFormat df = new SimpleDateFormat("E dd MMM");
            String formattedDate = df.format(c.getTime());
            options[i] = formattedDate;

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options);
        dateSelector.setAdapter(adapter);
        dateSelector.setSelection(0);
        dateSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    setWeather(position,"day");
                    TextView tv = ((TextView)parentView.getChildAt(0));
                    tv.setTextColor(Color.WHITE);
                    tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                    tv.setTypeface(null, Typeface.BOLD);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                selectedDate = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar(ActionBar ab) {
        ab.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDark));
        ab.setDisplayShowHomeEnabled(true);
        ab.setLogo(R.drawable.logo);
        ab.setDisplayUseLogoEnabled(true);
    }

    //récupérer les infos de la station qui a été sélectionnée dans la liste
    public void getWeatherInfo() {
        Requester r = new Requester(this);
        int currentStation = MainActivity.getCurrentStation();
        r.getWeathersStation(currentStation);
    }

    //maj de la variable contenant les météos / met à jour le nom de la station
    public static void updateWeathersInfos(ArrayList weathersList, Context context) {
        weathers = weathersList;
        cityName.setText(MainActivity.allStations.get(MainActivity.getCurrentStation()-1).getNomStation());
    }


    //ensemble de fonctions onClick des boutons de tranche de journée (matin/apres midi/soir/nuit)
    public void setToMorning(View v) {
        try {
            setWeather(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setToAfternoon(View v) {
        try {
            setWeather(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setToEvening(View v) {
        try {
            setWeather(3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setToNight(View v) {
        try {
            setWeather(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //met à jour les éléments graphiques afin qu'ils soient mis à jour lorsque l'utilisateur clique sur un bouton de tranche de journée
    public void setWeather(int moment) throws JSONException {
        JSONObject weather = null;
        try {
            weather = (JSONObject) weathers.get((moment-1)+(selectedDate*4));
        } catch (Exception e) {
                new AlertDialog.Builder(this)
                .setTitle("Erreur réseau")
                .setMessage("Une erreur est survenue. Vérifiez votre connexion internet ou contactez l'administrateur.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


        }
        temperature.setText(weather.get("temperature")+"°C");
        humidite.setText("Humidité : " + weather.get("humidite")+"%");
        meteoName.setText(weather.get("meteo_name")+"");
        Drawable myDrawable;
        switch (weather.getInt("fk_meteo_type")) {
            case 1 :
                myDrawable = getResources().getDrawable(R.drawable.w1);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 2 :
                myDrawable = getResources().getDrawable(R.drawable.w2);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 3 :
                myDrawable = getResources().getDrawable(R.drawable.w3);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 4 :
                myDrawable = getResources().getDrawable(R.drawable.w4);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 5 :
                myDrawable = getResources().getDrawable(R.drawable.w5);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 6 :
                myDrawable = getResources().getDrawable(R.drawable.w6);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 7 :
                myDrawable = getResources().getDrawable(R.drawable.w7);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 8 :
                myDrawable = getResources().getDrawable(R.drawable.w8);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 9 :
                myDrawable = getResources().getDrawable(R.drawable.w9);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 10 :
                myDrawable = getResources().getDrawable(R.drawable.w10);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 11 :
                myDrawable = getResources().getDrawable(R.drawable.w11);
                imageMeteo.setImageDrawable(myDrawable);
                break;

        }

    }

    //met à jour les éléments graphiques afin qu'ils soient mis à jour lorsque l'utilisateur change la date
    public void setWeather(int position, String mode) throws JSONException {
        //set la météo de jour sélectionné, en sachant que weathers.get(i) retourne une météo
        //get(0) va retourner jour actuel x nuit, get(2 + 4 * n) va renvoyer la météo après midi de jour suivant
        JSONObject weather = null;
        try {
            weather = (JSONObject) weathers.get(4*position);
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Erreur réseau")
                    .setMessage("Une erreur est survenue. Vérifiez votre connexion internet ou contactez l'administrateur.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        temperature.setText(weather.get("temperature")+"°C");
        humidite.setText("Humidité : " + weather.get("humidite")+"%");
        meteoName.setText(weather.get("meteo_name")+"");
        Drawable myDrawable;
        switch (weather.getInt("fk_meteo_type")) {
            case 1 :
                myDrawable = getResources().getDrawable(R.drawable.w1);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 2 :
                 myDrawable = getResources().getDrawable(R.drawable.w2);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 3 :
                 myDrawable = getResources().getDrawable(R.drawable.w3);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 4 :
                 myDrawable = getResources().getDrawable(R.drawable.w4);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 5 :
                 myDrawable = getResources().getDrawable(R.drawable.w5);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 6 :
                 myDrawable = getResources().getDrawable(R.drawable.w6);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 7 :
                 myDrawable = getResources().getDrawable(R.drawable.w7);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 8 :
                 myDrawable = getResources().getDrawable(R.drawable.w8);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 9 :
                 myDrawable = getResources().getDrawable(R.drawable.w9);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 10 :
                 myDrawable = getResources().getDrawable(R.drawable.w10);
                imageMeteo.setImageDrawable(myDrawable);
                break;
            case 11 :
                 myDrawable = getResources().getDrawable(R.drawable.w11);
                imageMeteo.setImageDrawable(myDrawable);
                break;

        }
    }

}
