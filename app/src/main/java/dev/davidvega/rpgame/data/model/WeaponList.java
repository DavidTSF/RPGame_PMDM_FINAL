package dev.davidvega.rpgame.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import dev.davidvega.rpgame.game.model.Weapon;

public class WeaponList {
    private ArrayList<Weapon> weaponList;

    public ArrayList<Weapon> getWeapons() { // Cambia el nombre del método a "getWeapons"
        return weaponList;
    }

    public void setWeapons(ArrayList<Weapon> weapons) { // Cambia el setter a "setWeapons"
        this.weaponList = weapons;
    }
}
