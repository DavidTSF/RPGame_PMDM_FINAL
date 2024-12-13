package dev.davidvega.rpgame.game.encounter;

/**
 * The type Trap.
 */
public class Trap {
    /**
     * The Name.
     */
    String name;
    /**
     * The Damage.
     */
    int damage;

    /**
     * The Dex level.
     */
    int dexLevel;

    /**
     * Instantiates a new Trap.
     *
     * @param name     the name
     * @param damage   the damage
     * @param dexLevel the dex level
     */
    public Trap(String name, int damage, int dexLevel) {
        this.name = name;
        this.damage = damage;
        this.dexLevel = dexLevel;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets damage.
     *
     * @param damage the damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Gets dex level.
     *
     * @return the dex level
     */
    public int getDexLevel() {
        return dexLevel;
    }

    /**
     * Sets dex level.
     *
     * @param dexLevel the dex level
     */
    public void setDexLevel(int dexLevel) {
        this.dexLevel = dexLevel;
    }
}
