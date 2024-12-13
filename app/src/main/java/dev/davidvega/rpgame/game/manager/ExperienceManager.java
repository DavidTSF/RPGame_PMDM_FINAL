package dev.davidvega.rpgame.game.manager;

import dev.davidvega.rpgame.game.model.PlayerCharacter;

/**
 * The type Experience manager.
 */
public class ExperienceManager {

    /**
     * Gets xp for next level.
     *
     * @param currentLevel the current level
     * @return the xp for next level
     */
    public static int getXpForNextLevel(int currentLevel) {
        return (int) (Math.pow(currentLevel, 2) * 10 + currentLevel * 35);
    }

    /**
     * Level up boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public static boolean levelUp(PlayerCharacter player) {
        int xpNeeded = getXpForNextLevel(player.getLevel());
        if (player.getXp() >= xpNeeded) {

            player.setLevel(player.getLevel() + 1);

            // Reducir la XP del jugador ya que ha subido de nivel
            player.setXp(player.getXp() - xpNeeded);

            switch (player.getPlayerClass()) {
                case Warrior:

                    //Estadisticas para el guerrero
                    player.setMaxHp(player.getMaxHp() + 4);
                    player.setMaxMana(player.getMaxMana() + 1);
                    player.setMana(player.getMaxMana());  // Restaurar al máximo
                    if ( player.getLevel() % 2 == 0  ) {
                        player.setStrength(player.getStrength() + 2);
                        player.setDexterity(player.getDexterity() + 1);
                        player.setIntelligence(player.getIntelligence() + 1);
                    }


                    break;
                case Mage:

                    //Estadisticas para el mago
                    player.setMaxHp(player.getMaxHp() + 1);
                    player.setMaxMana(player.getMaxMana() + 4);
                    player.setMana(player.getMaxMana()); // Restaurar al máximo
                    if ( player.getLevel() % 2 == 0  ) {
                        player.setStrength(player.getStrength() + 1);
                        player.setDexterity(player.getDexterity() + 1);
                        player.setIntelligence(player.getIntelligence() + 2);
                    }
                    break;
                case Rogue:

                    //Estadisticas para el picaro
                    player.setMaxHp(player.getMaxHp() + 2);
                    player.setMaxMana(player.getMaxMana() + 2);
                    player.setMana(player.getMaxMana()); // Restaurar al máximo
                    if ( player.getLevel() % 2 == 0  ) {
                        player.setStrength(player.getStrength() + 1);
                        player.setDexterity(player.getDexterity() + 2);
                        player.setIntelligence(player.getIntelligence() + 1);
                    }
                    break;


            }

            return true;

        }
        return false;
    }

    /**
     * Xp to next level int.
     *
     * @param player the player
     * @return the int
     */
    public static int xpToNextLevel(PlayerCharacter player) {
        return getXpForNextLevel(player.getLevel()) - player.getXp();
    }
}
