package dev.davidvega.rpgame.net.api;

import dev.davidvega.rpgame.game.model.Weapon;
import dev.davidvega.rpgame.game.model.Inventory;
import dev.davidvega.rpgame.game.model.PlayerCharacter;
import dev.davidvega.rpgame.login.Clase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlayerDataPrimitive {
    Clase playerClass;
    String name;
    int level;
    int xp;

    int maxHp;
    int hp;
    int defense;
    int maxMana;
    int mana;

    int strength;
    int dexterity;
    int intelligence;

    Weapon currentWeapon = new Weapon();

    Inventory inventory = new Inventory();;

    public PlayerDataPrimitive(PlayerCharacter pc) {
        this.playerClass = pc.getPlayerClass();
        this.name = pc.getName();
        this.level = pc.getLevel();
        this.xp = pc.getXp();
        this.maxHp = pc.getMaxHp();
        this.hp = pc.getHp();
        this.defense = pc.getDefense();
        this.maxMana = pc.getMaxMana();
        this.mana = pc.getMana();
        this.strength = pc.getStrength();
        this.dexterity = pc.getDexterity();
        this.intelligence = pc.getIntelligence();
        this.currentWeapon = pc.getCurrentWeapon();
        this.inventory = pc.getInventory();
    }

    public PlayerCharacter toPlayerCharacter() {
        return new PlayerCharacter(playerClass, name, level, xp, maxHp, hp, defense, maxMana, mana, strength, dexterity, intelligence, currentWeapon, inventory);

    }

}