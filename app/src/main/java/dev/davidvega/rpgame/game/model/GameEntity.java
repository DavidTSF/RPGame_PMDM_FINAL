package dev.davidvega.rpgame.game.model;

public interface GameEntity {
    int hp = 0;
    int attack = 0;
    int defense = 0;
    int level = 0;

    String getName();
    int getAttack();
    int getHp();
    int getDefense();
    int getLevel();
    void setHp(int hp);



}
