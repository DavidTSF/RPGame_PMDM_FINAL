package dev.davidvega.rpgame.game.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

import dev.davidvega.rpgame.login.Clase;

public class PlayerCharacter implements Serializable, GameEntity {
    Clase playerClass;
    String name;
    int level;

    int maxHp;
    int hp;
    int defense;
    int maxMana;
    int mana;

    int strength;
    int dexterity;
    int intelligence;

    Weapon currentWeapon = new Weapon(0, "puños", 0, 1, "Tienes tus puños...", null );

    MutableLiveData<Inventory> inventory = new MutableLiveData<>(new Inventory());

    public PlayerCharacter() {

    }
    public PlayerCharacter(Clase playerClass) {
        this.playerClass = playerClass;
    }

    public PlayerCharacter(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public PlayerCharacter(String name, int level, int maxHp, int hp, int defense, int mana, int strength, int dexterity, int intelligence, Inventory inventory) {
        this.name = name;
        this.level = level;
        this.maxHp = maxHp;
        this.hp = hp;
        this.defense = defense;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.inventory.postValue(inventory);
    }

    public PlayerCharacter(String name, int level, int maxHp, int hp, int defense, int mana, int strength, int dexterity, int intelligence, Weapon currentWeapon, Inventory inventory) {
        this.name = name;
        this.level = level;
        this.maxHp = maxHp;
        this.hp = hp;
        this.defense = defense;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.currentWeapon = currentWeapon;
        this.inventory.postValue(inventory);
    }

    public static PlayerCharacter baseCharacter ( String name ) {
        PlayerCharacter pc = new PlayerCharacter();
        pc.setName(name);

        pc.setLevel(1);

        pc.setHp(10);
        pc.setMaxHp(10);

        pc.setMana(5);
        pc.setMaxMana(5);

        pc.setDefense(5);
        pc.setDexterity(5);
        pc.setStrength(5);
        pc.setIntelligence(5);

        pc.inventory.postValue(new Inventory());

        return pc;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getAttack() {
        int attack = 1;

        switch (playerClass) {
            case Warrior:
                attack += (int) ((double) strength / 2);
                attack += (int) ((double) dexterity / 4);
                attack += (int) ((double) intelligence / 6);
                break;
            case Mage:
                attack += (int) ((double) strength / 6);
                attack += (int) ((double) dexterity / 4);
                attack += (int) ((double) intelligence / 2);
                break;
            case Rogue:
                attack += (int) ((double) strength / 4);
                attack += (int) ((double) dexterity / 2);
                attack += (int) ((double) intelligence / 6);
                break;
        }

        attack *= Math.max(level / 2, 1);

        return attack;
    }

    public int getHp() {
        return hp;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public Inventory getInventory() {
        return inventory.getValue();
    }

    public MutableLiveData<Inventory> getInventoryLiveData() {
        return inventory;
    }

    public void setInventory(MutableLiveData<Inventory> inventory) {
        this.inventory = inventory;
    }

    public Clase getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(Clase playerClass) {
        this.playerClass = playerClass;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PlayerCharacter {")
                .append("\n    Name: ").append(name)
                .append("\n    Class: ").append(playerClass)
                .append("\n    Level: ").append(level)
                .append("\n    HP: ").append(hp).append("/").append(maxHp)
                .append("\n    Mana: ").append(mana).append("/").append(maxMana)
                .append("\n    Defense: ").append(defense)
                .append("\n    Strength: ").append(strength)
                .append("\n    Dexterity: ").append(dexterity)
                .append("\n    Intelligence: ").append(intelligence)
                .append("\n    Current Weapon: ").append(currentWeapon != null ? currentWeapon : "None")
                .append("\n    Inventory: ").append(inventory != null ? inventory : "Empty")
                .append("\n}");
        return sb.toString();
    }


}
