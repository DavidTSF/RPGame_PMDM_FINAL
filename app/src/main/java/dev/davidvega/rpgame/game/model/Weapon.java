package dev.davidvega.rpgame.game.model;
import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Weapon.
 */
@Getter
@Setter
@AllArgsConstructor
@JsonTypeName("WEAPON")
public class Weapon extends BaseItem {
    /**
     * The Id weapon.
     */
    public int id_weapon;
    /**
     * The Base damage.
     */
    public int base_damage;

    /**
     * Instantiates a new Weapon.
     */
    public Weapon() {
        super(ItemType.WEAPON);
    }

    /**
     * Instantiates a new Weapon.
     *
     * @param id_weapon   the id weapon
     * @param itemName    the item name
     * @param level       the level
     * @param base_damage the base damage
     * @param description the description
     * @param image       the image
     * @param itemType    the item type
     */
    public Weapon(int id_weapon, String itemName, int level, int base_damage, String description, String image, ItemType itemType) {
        super(itemName, level, description, image, itemType);
        this.id_weapon = id_weapon;
        this.base_damage = base_damage;
    }

    /**
     * Instantiates a new Weapon.
     *
     * @param id_weapon   the id weapon
     * @param itemName    the item name
     * @param level       the level
     * @param base_damage the base damage
     * @param description the description
     * @param image       the image
     */
    public Weapon(int id_weapon, String itemName, int level, int base_damage, String description, String image) {
        super(itemName, level, description, image, ItemType.WEAPON);
        this.id_weapon = id_weapon;
        this.base_damage = base_damage;
    }

    /**
     * Instantiates a new Weapon.
     *
     * @param id_weapon   the id weapon
     * @param itemName    the item name
     * @param level       the level
     * @param base_damage the base damage
     * @param description the description
     */
    public Weapon(int id_weapon, String itemName, int level, int base_damage, String description) {
        super(itemName, level, description, ItemType.WEAPON);
        this.id_weapon = id_weapon;
        this.base_damage = base_damage;

    }

    /**
     * Gets id weapon.
     *
     * @return the id weapon
     */
    public int getId_weapon() {
        return id_weapon;
    }

    /**
     * Sets id weapon.
     *
     * @param id_weapon the id weapon
     */
    public void setId_weapon(int id_weapon) {
        this.id_weapon = id_weapon;
    }

    /**
     * Gets base damage.
     *
     * @return the base damage
     */
    public int getBase_damage() {
        return base_damage;
    }

    /**
     * Sets base damage.
     *
     * @param base_damage the base damage
     */
    public void setBase_damage(int base_damage) {
        this.base_damage = base_damage;
    }


    /**
     * Parse weapon weapon.
     *
     * @param item the item
     * @return the weapon
     */
    public static Weapon parseWeapon(Item item) {
        return (Weapon) item;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Weapon {")
                .append("\n  ID: ").append(id_weapon)
                .append("\n  Name: ").append(itemName)
                .append("\n  Level: ").append(level)
                .append("\n  Base Damage: ").append(base_damage)
                .append("\n  Description: ").append(description)
                .append("\n  Image: ").append(image != null ? "Image present" : "No image")
                .append("\n}");
        return sb.toString();
    }


}
