package com.main.mtorolog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        this.setupActionBar(getSupportActionBar());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

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
            case R.id.action_home:
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            CharSequence[] entries = new CharSequence[MainActivity.allStations.size()];
            CharSequence[] entryValues = new CharSequence[MainActivity.allStations.size()];
            for(int i = 0; i< MainActivity.allStations.size();i++) {
                entries[i] = MainActivity.allStations.get(i).getNomStation();
                entryValues[i] = String.valueOf(MainActivity.allStations.get(i).getId());
            }
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            ListPreference stationsPref = findPreference("station");
            stationsPref.setEntries(entries);
            stationsPref.setEntryValues(entryValues);
        }
    }



}