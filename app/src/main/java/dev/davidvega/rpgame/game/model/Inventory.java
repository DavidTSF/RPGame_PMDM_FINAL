package dev.davidvega.rpgame.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Inventory.
 */
public class Inventory implements Serializable {
    private List<Item> inventoryList = new ArrayList<>();

    /**
     * Instantiates a new Inventory.
     */
    public Inventory() {
    }

    /**
     * Instantiates a new Inventory.
     *
     * @param inventoryList the inventory list
     */
    public Inventory(List<Item> inventoryList) {
        this.inventoryList = inventoryList;
    }

    /**
     * Add item.
     *
     * @param item the item
     */
    public void addItem (Item item ) {
        inventoryList.add(item);
    }

    /**
     * Gets all weapon.
     *
     * @return the all weapon
     */
    @JsonIgnore
    public List<Weapon> getAllWeapon() {
        return inventoryList.stream()
                .filter(e -> e.getItemType().equals(Item.ItemType.WEAPON) )
                .map(Weapon::parseWeapon)
                .collect(Collectors.toList());
    }

    /**
     * Gets inventory list.
     *
     * @return the inventory list
     */
    public List<Item> getInventoryList() {
        return inventoryList;
    }

    /**
     * Sets inventory list.
     *
     * @param inventoryList the inventory list
     */
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
