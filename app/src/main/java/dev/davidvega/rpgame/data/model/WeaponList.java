package dev.davidvega.rpgame.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import dev.davidvega.rpgame.game.model.Weapon;

public class WeaponList {
    @SerializedName("weapons")
    @Expose
    private ArrayList<Weapon> weaponList;

    public ArrayList<Weapon> getResults() {
        return weaponList;
    }
    public void setResults(ArrayList<Weapon> results) {
        this.weaponList = results;
    }
}
