package com.main.mtorolog;

import android.content.Context;

import java.net.ContentHandler;

public class Station {
    private int id;
    private String nomStation;
    private Ville ville;

    public Station(int id, String nomStation, int fk_ville, Context context) {
        this.id = id;
        this.nomStation = nomStation;
        this.ville = new Ville(fk_ville,context);
    }

    public int getId() {
        return id;
    }

    public String getNomStation() {
        return nomStation;
    }

    public Ville getVille() {
        return ville;
    }

    @Override
    public String toString() {
        return this.nomStation ;
    }

}
