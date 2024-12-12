package dev.davidvega.rpgame.game.encounter;

import dev.davidvega.rpgame.game.model.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enemy implements GameEntity {
    String name;

    int attack;
    int maxhp;
    int hp;
    int defense;



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

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
