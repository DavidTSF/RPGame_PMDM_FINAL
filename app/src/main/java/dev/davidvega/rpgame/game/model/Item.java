package dev.davidvega.rpgame.game.model;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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

    String getDescription();
    void setImage(String image);
    String getImage();
    String getItemName();
    ItemType getItemType();
    @NonNull
    String toString();

    public enum ItemType {
        WEAPON,
        CONSUMABLE,
        ARMOR,
        GENERIC
    }
}
