package dev.davidvega.rpgame.game.model;

import androidx.annotation.NonNull;

public interface Item {

    public String getItemName();
    public ItemType getItemType();
    @NonNull
    public String toString();

    public enum ItemType {
        WEAPON,
        CONSUMABLE,
        ARMOR
    }
}
