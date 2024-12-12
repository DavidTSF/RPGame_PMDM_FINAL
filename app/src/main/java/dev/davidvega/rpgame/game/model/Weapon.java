package dev.davidvega.rpgame.game.model;
import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonTypeName("WEAPON")
public class Weapon extends BaseItem {
    public int id_weapon;
    public int base_damage;

    public Weapon() {
    }

    public Weapon(int id_weapon, String itemName, int level, int base_damage, String description, String image, ItemType itemType) {
        super(itemName, level, description, image, itemType);
        this.id_weapon = id_weapon;
        this.base_damage = base_damage;
    }

    public Weapon(int id_weapon, String itemName, int level, int base_damage, String description, String image) {
        super(itemName, level, description, image, ItemType.WEAPON);
        this.id_weapon = id_weapon;
        this.base_damage = base_damage;
    }
    public Weapon(int id_weapon, String itemName, int level, int base_damage, String description) {
        super(itemName, level, description, ItemType.WEAPON);
        this.id_weapon = id_weapon;
        this.base_damage = base_damage;

    }

    public int getId_weapon() {
        return id_weapon;
    }

    public void setId_weapon(int id_weapon) {
        this.id_weapon = id_weapon;
    }

    public int getBase_damage() {
        return base_damage;
    }

    public void setBase_damage(int base_damage) {
        this.base_damage = base_damage;
    }


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
