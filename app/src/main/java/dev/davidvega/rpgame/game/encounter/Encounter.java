package dev.davidvega.rpgame.game.encounter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Encounter.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Encounter {
    /**
     * The Description.
     */
    String description;
    /**
     * The Encounter type.
     */
    typeEncounter encounterType;
    /**
     * The Difficulty.
     */
    int difficulty = 1;
    /**
     * The Enemy.
     */
    Enemy enemy;
    /**
     * The Trap.
     */
    Trap trap;

    /**
     * The enum Type encounter.
     */
    public enum typeEncounter {
        /**
         * Combat type encounter.
         */
        COMBAT,
        /**
         * Trap type encounter.
         */
        TRAP,
        /**
         * Challenge type encounter.
         */
        CHALLENGE,
        /**
         * Start type encounter.
         */
        START
    }

    /**
     * Instantiates a new Encounter.
     *
     * @param description   the description
     * @param encounterType the encounter type
     */
    public Encounter(String description, typeEncounter encounterType) {
        this.description = description;
        this.encounterType = encounterType;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(encounterType.toString()).append("]");
        switch ( this.encounterType ) {
            case COMBAT:
                sb.append("Enemy: ").append(enemy.name);
                break;
            case TRAP:
                sb.append("Trap: ").append(trap.name);
                break;
        }

        sb.append(description);
        return sb.toString();
    }

}
