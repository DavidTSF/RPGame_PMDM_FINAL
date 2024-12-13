package dev.davidvega.rpgame.game.model;


import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Consumable.
 */
@Getter
@Setter
@AllArgsConstructor
@JsonTypeName("CONSUMABLE")
public class Consumable extends BaseItem {
    /**
     * The Id consumable.
     */
    int id_consumable;
    /**
     * The Power.
     */
    int power;

    /**
     * The Consumable type.
     */
    @JsonProperty("consumable_type")
    CONSUMABLE_TYPE consumableType;

    /**
     * Instantiates a new Consumable.
     */
    public Consumable() {
        super(ItemType.CONSUMABLE);
    }

    /**
     * The enum Consumable type.
     */
    public enum CONSUMABLE_TYPE {
        /**
         * Healing consumable type.
         */
        HEALING,
        /**
         * Buff consumable type.
         */
        BUFF
    }

    /**
     * Gets total consumable power.
     *
     * @return the total consumable power
     */
    @JsonIgnore
    public int getTotalConsumablePower() {
        return power * Math.max(level / 2, 1);
    }

    /**
     * Heal player player character.
     *
     * @param playerCharacter the player character
     * @return the player character
     */
    public PlayerCharacter healPlayer(PlayerCharacter playerCharacter) {
        int totalHeal = getTotalConsumablePower();
        Log.d("DEBUG_CONSUMABLE_TOTAL_HEAL", String.valueOf(totalHeal));
        Log.d("DEBUG_CONSUMABLE_TOTAL_POWER", this.toString());

        if ( playerCharacter.getHp()+totalHeal < playerCharacter.getMaxHp() ) {
            playerCharacter.setHp(playerCharacter.getHp()+totalHeal);
        } else {
            playerCharacter.setHp(playerCharacter.getMaxHp());
        }
        return playerCharacter;
    }
}
