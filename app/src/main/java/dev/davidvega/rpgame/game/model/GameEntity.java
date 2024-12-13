package dev.davidvega.rpgame.game.model;

/**
 * The interface Game entity.
 */
public interface GameEntity {
    /**
     * The constant hp.
     */
    int hp = 0;
    /**
     * The constant attack.
     */
    int attack = 0;
    /**
     * The constant defense.
     */
    int defense = 0;
    /**
     * The constant level.
     */
    int level = 0;

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets attack.
     *
     * @return the attack
     */
    int getAttack();

    /**
     * Gets hp.
     *
     * @return the hp
     */
    int getHp();

    /**
     * Gets defense.
     *
     * @return the defense
     */
    int getDefense();

    /**
     * Gets level.
     *
     * @return the level
     */
    int getLevel();

    /**
     * Sets hp.
     *
     * @param hp the hp
     */
    void setHp(int hp);



}
