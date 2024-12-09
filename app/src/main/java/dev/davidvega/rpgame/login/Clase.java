package dev.davidvega.rpgame.login;

public enum Clase {
    Warrior("Guerrero"),
    Rogue("Pícaro"),
    Mage("Mago");

    private String name;

    // Constructor para asignar el nombre
    Clase(String name) {
        this.name = name;
    }

    // Método estático para obtener el enum desde un string
    public static Clase fromString(String text) {
        for (Clase clase : Clase.values()) {
            if (clase.name.equalsIgnoreCase(text)) {
                return clase;
            }
        }
        throw new IllegalArgumentException("No enum constant for " + text);
    }

    public String getName() {
        return name;
    }
}
