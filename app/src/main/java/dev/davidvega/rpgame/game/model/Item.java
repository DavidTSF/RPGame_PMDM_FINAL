package dev.davidvega.rpgame.game.model;

public interface Item {

    public String getItemName();
    public ItemType getItemType();

    public enum ItemType {
        WEAPON,
        CONSUMABLE,
        ARMOR
    }
}
