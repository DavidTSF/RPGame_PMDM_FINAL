package dev.davidvega.rpgame.game.model;
import androidx.annotation.NonNull;

public class Weapon implements Item {
    public int id_weapon;
    public String name;
    public int level;
    public int base_damage;
    public String description;
    public String image;

    public Weapon() {
    }

    public Weapon(int id_weapon, String name, int level, int base_damage, String description, String image) {
        this.id_weapon = id_weapon;
        this.name = name;
        this.level = level;
        this.base_damage = base_damage;
        this.description = description;
        this.image = image;
    }

    public Weapon(int id_weapon, String name, int level, int base_damage, String description) {
        this.id_weapon = id_weapon;
        this.name = name;
        this.level = level;
        this.base_damage = base_damage;
        this.description = description;
    }

    public int getId_weapon() {
        return id_weapon;
    }

    public void setId_weapon(int id_weapon) {
        this.id_weapon = id_weapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBase_damage() {
        return base_damage;
    }

    public void setBase_damage(int base_damage) {
        this.base_damage = base_damage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String getItemName() {
        return name;
    }
    @Override
    public ItemType getItemType() {
        return ItemType.WEAPON;
    }

    public static Weapon parseWeapon(Item item) {
        return (Weapon) item;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Weapon {")
                .append("\n  ID: ").append(id_weapon)
                .append("\n  Name: ").append(name)
                .append("\n  Level: ").append(level)
                .append("\n  Base Damage: ").append(base_damage)
                .append("\n  Description: ").append(description)
                .append("\n  Image: ").append(image != null ? "Image present" : "No image")
                .append("\n}");
        return sb.toString();
    }


}
