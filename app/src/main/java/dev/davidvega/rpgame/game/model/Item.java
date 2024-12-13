package dev.davidvega.rpgame.game.model;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The interface Item.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Weapon.class, name = "WEAPON"),
        @JsonSubTypes.Type(value = Consumable.class, name = "CONSUMABLE"),
        @JsonSubTypes.Type(value = BaseItem.class, name = "GENERIC")
})
public interface Item {

    /**
     * Gets description.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Sets image.
     *
     * @param image the image
     */
    void setImage(String image);

    /**
     * Gets image.
     *
     * @return the image
     */
    String getImage();

    /**
     * Gets item name.
     *
     * @return the item name
     */
    String getItemName();

    /**
     * Gets item type.
     *
     * @return the item type
     */
    ItemType getItemType();
    @NonNull
    String toString();

    /**
     * The enum Item type.
     */
    public enum ItemType {
        /**
         * Weapon item type.
         */
        WEAPON,
        /**
         * Consumable item type.
         */
        CONSUMABLE,
        /**
         * Armor item type.
         */
        ARMOR,
        /**
         * Generic item type.
         */
        GENERIC
    }
}
