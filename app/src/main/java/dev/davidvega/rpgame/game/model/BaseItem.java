package dev.davidvega.rpgame.game.model;


import static dev.davidvega.rpgame.game.model.Item.ItemType.GENERIC;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Base item.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseItem implements Item {
    /**
     * The Item name.
     */
    @JsonProperty("name")
    String itemName;

    /**
     * The Level.
     */
    int level;
    /**
     * The Description.
     */
    String description;
    /**
     * The Image.
     */
    String image;

    /**
     * The Item type.
     */
    @JsonProperty("type")
    ItemType itemType;

    /**
     * Instantiates a new Base item.
     *
     * @param itemName    the item name
     * @param level       the level
     * @param description the description
     * @param itemType    the item type
     */
    public BaseItem(String itemName, int level, String description, ItemType itemType) {
        this.itemName = itemName;
        this.level = level;
        this.description = description;
        this.itemType = itemType;
    }

    /**
     * Instantiates a new Base item.
     *
     * @param itemType the item type
     */
    public BaseItem(ItemType itemType) {
        this.itemType = itemType;
    }

    @JsonProperty("name")
    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }


    /**
     * Sets item name.
     *
     * @param itemName the item name
     */
    @JsonProperty("name")
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Sets item type.
     *
     * @param itemType the item type
     */
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
