package dev.davidvega.rpgame.game.encounter;

import dev.davidvega.rpgame.game.model.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The type Enemy.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enemy implements GameEntity {
    /**
     * The Name.
     */
    String name;

    /**
     * The Attack.
     */
    int attack;
    /**
     * The Maxhp.
     */
    int maxhp;
    /**
     * The Hp.
     */
    int hp;
    /**
     * The Defense.
     */
    int defense;


    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAttack() {
        return attack;
    }


    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    /**
     * Sets attack.
     *
     * @param attack the attack
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Sets maxhp.
     *
     * @param maxhp the maxhp
     */
    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    /**
     * Sets defense.
     *
     * @param defense the defense
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }
}
