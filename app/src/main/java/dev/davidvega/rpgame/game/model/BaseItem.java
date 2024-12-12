package dev.davidvega.rpgame.game.model;


import static dev.davidvega.rpgame.game.model.Item.ItemType.GENERIC;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseItem implements Item {
    @JsonProperty("name")
    String itemName;

    int level;
    String description;
    String image;

    @JsonProperty("type")
    ItemType itemType;

    public BaseItem(String itemName, int level, String description, ItemType itemType) {
        this.itemName = itemName;
        this.level = level;
        this.description = description;
        this.itemType = itemType;
    }

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


    @JsonProperty("name")
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
