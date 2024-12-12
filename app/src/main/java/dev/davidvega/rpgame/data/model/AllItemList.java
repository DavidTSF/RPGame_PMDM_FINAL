package dev.davidvega.rpgame.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import dev.davidvega.rpgame.game.model.Consumable;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.game.model.Weapon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllItemList {

    List<Weapon> weapons = new ArrayList<>();
    List<Consumable> consumables = new ArrayList<>();

    @JsonIgnore
    List<Item> allItems = new ArrayList<>();

    public List<Item> getAllItems() {
        allItems.clear();
        allItems.addAll(weapons);
        allItems.addAll(consumables);
        return allItems;
    }

}
