package dev.davidvega.rpgame.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory implements Serializable {
    private List<Item> inventoryList = new ArrayList<>();

    public Inventory() {
    }

    public Inventory(List<Item> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public void addItem (Item item ) {
        inventoryList.add(item);
    }

    @JsonIgnore
    public List<Weapon> getAllWeapon() {
        return inventoryList.stream()
                .filter(e -> e.getItemType().equals(Item.ItemType.WEAPON) )
                .map(Weapon::parseWeapon)
                .collect(Collectors.toList());
    }

    public List<Item> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Item> inventoryList) {
        this.inventoryList = inventoryList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventory {")
                .append("\n  Items: ")
                .append(inventoryList.isEmpty() ? "No items" : "\n    " + inventoryList.stream()
                        .map(Item::toString)
                        .collect(Collectors.joining("\n    ")))
                .append("\n}");
        return sb.toString();
    }

}
