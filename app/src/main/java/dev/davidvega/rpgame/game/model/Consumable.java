package dev.davidvega.rpgame.game.model;


import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonTypeName("CONSUMABLE")
public class Consumable extends BaseItem {
    int id_consumable;
    int power;

    @JsonProperty("consumable_type")
    CONSUMABLE_TYPE consumableType;

    public Consumable() {
        super(ItemType.CONSUMABLE);
    }

    public enum CONSUMABLE_TYPE {
        HEALING,
        BUFF
    }

    @JsonIgnore
    public int getTotalConsumablePower() {
        return power * Math.max(level / 2, 1);
    }

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
