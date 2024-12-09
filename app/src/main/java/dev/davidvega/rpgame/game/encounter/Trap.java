package dev.davidvega.rpgame.game.encounter;

public class Trap {
    String name;
    int damage;

    int dexLevel;

    public Trap(String name, int damage, int dexLevel) {
        this.name = name;
        this.damage = damage;
        this.dexLevel = dexLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDexLevel() {
        return dexLevel;
    }

    public void setDexLevel(int dexLevel) {
        this.dexLevel = dexLevel;
    }
}
