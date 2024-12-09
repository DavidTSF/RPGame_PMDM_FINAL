package dev.davidvega.rpgame.game.encounter;

import dev.davidvega.rpgame.game.model.GameEntity;

public class Enemy implements GameEntity {
    String name;

    int attack;
    int maxhp;
    int hp;
    int defense;

    public Enemy(String name, int attack, int hp, int maxhp, int defense) {
        this.name = name;
        this.attack = attack;
        this.hp = hp;
        this.defense = defense;
        this.maxhp = maxhp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
