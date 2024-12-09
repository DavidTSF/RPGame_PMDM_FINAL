package dev.davidvega.rpgame.game.model;

import java.io.Serializable;

import dev.davidvega.rpgame.data.model.Weapon;
import dev.davidvega.rpgame.login.Clase;

public class PlayerCharacter implements Serializable, GameEntity {
    Clase playerClass;
    String name;
    int level;

    int maxhp;
    int hp;
    int defense;
    int mana;

    int strength;
    int dexterity;
    int intelligence;


    Weapon currentWeapon = new Weapon(0, "puños", 0, 1, "Tienes tus puños...", null );

    Inventory inventory = new Inventory();

    public PlayerCharacter() {

    }
    public PlayerCharacter(Clase playerClass) {
        this.playerClass = playerClass;
    }

    public PlayerCharacter(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public PlayerCharacter(String name, int level, int maxhp, int hp, int defense, int mana, int strength, int dexterity, int intelligence, Inventory inventory) {
        this.name = name;
        this.level = level;
        this.maxhp = maxhp;
        this.hp = hp;
        this.defense = defense;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.inventory = inventory;
    }

    public PlayerCharacter(String name, int level, int maxhp, int hp, int defense, int mana, int strength, int dexterity, int intelligence, Weapon currentWeapon, Inventory inventory) {
        this.name = name;
        this.level = level;
        this.maxhp = maxhp;
        this.hp = hp;
        this.defense = defense;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.currentWeapon = currentWeapon;
        this.inventory = inventory;
    }

    public static PlayerCharacter baseCharacter ( String name ) {
        PlayerCharacter pc = new PlayerCharacter();
        pc.setName(name);

        pc.setLevel(1);

        pc.setHp(10);
        pc.setMaxhp(10);

        pc.setDefense(5);
        pc.setMana(5);

        pc.setDexterity(5);
        pc.setStrength(5);
        pc.setIntelligence(5);

        pc.setInventory(new Inventory());

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
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Clase getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(Clase playerClass) {
        this.playerClass = playerClass;
    }

    public int getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }
}
