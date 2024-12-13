package dev.davidvega.rpgame.login;

/**
 * The enum Clase.
 */
public enum Clase {
    /**
     * Warrior clase.
     */
    Warrior("Guerrero"),
    /**
     * Rogue clase.
     */
    Rogue("Pícaro"),
    /**
     * Mage clase.
     */
    Mage("Mago");

    private String name;

    // Constructor para asignar el nombre
    Clase(String name) {
        this.name = name;
    }

    /**
     * From string clase.
     *
     * @param text the text
     * @return the clase
     */
// Método estático para obtener el enum desde un string
    public static Clase fromString(String text) {
        for (Clase clase : Clase.values()) {
            if (clase.name.equalsIgnoreCase(text)) {
                return clase;
            }
        }
        throw new IllegalArgumentException("No enum constant for " + text);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
