package dev.davidvega.rpgame.game.encounter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Encounter {
    String description;
    typeEncounter encounterType;
    int difficulty = 1;
    Enemy enemy;
    Trap trap;

    public enum typeEncounter {
        COMBAT,
        TRAP,
        CHALLENGE,
        START
    }

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
