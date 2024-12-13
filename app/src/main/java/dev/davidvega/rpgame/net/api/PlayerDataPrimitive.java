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


/**
 * The type Player data primitive.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlayerDataPrimitive {
    /**
     * The Player class.
     */
    Clase playerClass;
    /**
     * The Name.
     */
    String name;
    /**
     * The Level.
     */
    int level;
    /**
     * The Xp.
     */
    int xp;

    /**
     * The Max hp.
     */
    int maxHp;
    /**
     * The Hp.
     */
    int hp;
    /**
     * The Defense.
     */
    int defense;
    /**
     * The Max mana.
     */
    int maxMana;
    /**
     * The Mana.
     */
    int mana;

    /**
     * The Strength.
     */
    int strength;
    /**
     * The Dexterity.
     */
    int dexterity;
    /**
     * The Intelligence.
     */
    int intelligence;

    /**
     * The Current weapon.
     */
    Weapon currentWeapon = new Weapon();

    /**
     * The Inventory.
     */
    Inventory inventory = new Inventory();;

    /**
     * Instantiates a new Player data primitive.
     *
     * @param pc the pc
     */
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

    /**
     * To player character player character.
     *
     * @return the player character
     */
    public PlayerCharacter toPlayerCharacter() {
        return new PlayerCharacter(playerClass, name, level, xp, maxHp, hp, defense, maxMana, mana, strength, dexterity, intelligence, currentWeapon, inventory);

    }

}