package dev.davidvega.rpgame.game.model;

import static dev.davidvega.rpgame.MainActivity.fistPngPath;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

import dev.davidvega.rpgame.login.Clase;
import dev.davidvega.rpgame.utils.ImageUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The type Player character.
 */
@Getter
@Setter
@AllArgsConstructor
public class PlayerCharacter implements Serializable, GameEntity {
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
    Weapon currentWeapon = new Weapon(0, "puños", 0, 1  ,"Tienes tus puños...",
            fistPngPath,
            Item.ItemType.WEAPON );

    /**
     * The Inventory.
     */
    MutableLiveData<Inventory> inventory = new MutableLiveData<>(new Inventory());

    /**
     * Instantiates a new Player character.
     */
    public PlayerCharacter() {

    }

    /**
     * Instantiates a new Player character.
     *
     * @param playerClass   the player class
     * @param name          the name
     * @param level         the level
     * @param xp            the xp
     * @param maxHp         the max hp
     * @param hp            the hp
     * @param defense       the defense
     * @param maxMana       the max mana
     * @param mana          the mana
     * @param strength      the strength
     * @param dexterity     the dexterity
     * @param intelligence  the intelligence
     * @param currentWeapon the current weapon
     * @param inventory     the inventory
     */
    public PlayerCharacter(Clase playerClass, String name, int level, int xp, int maxHp, int hp, int defense, int maxMana, int mana, int strength, int dexterity, int intelligence, Weapon currentWeapon, Inventory inventory) {
        this.playerClass = playerClass;
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.maxHp = maxHp;
        this.hp = hp;
        this.defense = defense;
        this.maxMana = maxMana;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.currentWeapon = currentWeapon;
        this.inventory.setValue(inventory);
    }

    /**
     * Base character player character.
     *
     * @param name the name
     * @return the player character
     */
    public static PlayerCharacter baseCharacter ( String name ) {
        PlayerCharacter pc = new PlayerCharacter();
        pc.setName(name);
        pc.setXp(0);

        pc.setLevel(1);

        pc.setHp(10);
        pc.setMaxHp(10);

        pc.setMana(5);
        pc.setMaxMana(5);

        pc.setDefense(5);
        pc.setDexterity(5);
        pc.setStrength(5);
        pc.setIntelligence(5);

        pc.inventory.postValue(new Inventory());

        return pc;
    }


    @Override
    public int getAttack() {
        int attack = 1 + getCurrentWeapon().base_damage;

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

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory.getValue();
    }

    /**
     * Gets inventory live data.
     *
     * @return the inventory live data
     */
    public MutableLiveData<Inventory> getInventoryLiveData() {
        return inventory;
    }


    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        inventory.getValue().toString();
        sb.append("PlayerCharacter {")
                .append("\n    Name: ").append(name)
                .append("\n    Class: ").append(playerClass)
                .append("\n    Level: ").append(level)
                .append("\n    HP: ").append(hp).append("/").append(maxHp)
                .append("\n    Mana: ").append(mana).append("/").append(maxMana)
                .append("\n    Defense: ").append(defense)
                .append("\n    Strength: ").append(strength)
                .append("\n    Dexterity: ").append(dexterity)
                .append("\n    Intelligence: ").append(intelligence)
                .append("\n    Current Weapon: ").append(currentWeapon != null ? currentWeapon : "None")
                .append("\n    Inventory: ").append(inventory.getValue().toString())
                .append("\n}");
        return sb.toString();
    }


}
