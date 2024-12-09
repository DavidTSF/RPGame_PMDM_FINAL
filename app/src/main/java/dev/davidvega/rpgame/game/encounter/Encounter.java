package dev.davidvega.rpgame.game.encounter;

public class Encounter {
    String description;
    typeEncounter encounterType;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public typeEncounter getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(typeEncounter encounterType) {
        this.encounterType = encounterType;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Trap getTrap() {
        return trap;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
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
