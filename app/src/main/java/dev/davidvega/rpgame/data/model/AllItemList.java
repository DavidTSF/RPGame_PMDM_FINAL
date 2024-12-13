package dev.davidvega.rpgame.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import dev.davidvega.rpgame.game.model.Consumable;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.game.model.Weapon;
import lombok.Getter;
import lombok.Setter;

/**
 * The type All item list.
 */
@Getter
@Setter
public class AllItemList {

    /**
     * The Weapons.
     */
    List<Weapon> weapons = new ArrayList<>();
    /**
     * The Consumables.
     */
    List<Consumable> consumables = new ArrayList<>();

    /**
     * The All items.
     */
    @JsonIgnore
    List<Item> allItems = new ArrayList<>();

    /**
     * Gets all items.
     *
     * @return the all items
     */
    public List<Item> getAllItems() {
        allItems.clear();
        allItems.addAll(weapons);
        allItems.addAll(consumables);
        return allItems;
    }

}
