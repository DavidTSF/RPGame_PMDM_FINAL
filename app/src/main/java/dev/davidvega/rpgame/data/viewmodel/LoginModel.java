package dev.davidvega.rpgame.data.viewmodel;

import dev.davidvega.rpgame.data.model.User;
import dev.davidvega.rpgame.game.model.PlayerCharacter;
import dev.davidvega.rpgame.login.Clase;

/**
 * The type Login model.
 */
public class LoginModel {

    /**
     * The interface Login callback.
     */
    interface LoginCallback {
        /**
         * On created character with class.
         *
         * @param user the user
         */
        void onCreatedCharacterWithClass(User user);
    }

    /**
     * Create character with class.
     *
     * @param user          the user
     * @param characterName the character name
     * @param loginCallback the login callback
     */
    public void createCharacterWithClass(User user, String characterName, LoginCallback loginCallback) {

        PlayerCharacter playerCharacter = PlayerCharacter.baseCharacter(characterName);

        if ( user.getPlayerdataLiveData().getValue().getPlayerClass() == null ) {
            return;
        }

        Clase clase = user.getPlayerdataLiveData().getValue().getPlayerClass();
        playerCharacter.setPlayerClass(clase);

        switch ( clase ) {
            case Warrior:
                playerCharacter.setIntelligence(4);
                playerCharacter.setDexterity(6);
                playerCharacter.setStrength(8);

                playerCharacter.setMaxHp(10);
                playerCharacter.setHp(10);
                playerCharacter.setDefense(8);
                playerCharacter.setMaxMana(4);
                playerCharacter.setMana(4);
                break;
            case Rogue:
                playerCharacter.setIntelligence(4);
                playerCharacter.setDexterity(8);
                playerCharacter.setStrength(6);

                playerCharacter.setMaxHp(9);
                playerCharacter.setHp(9);
                playerCharacter.setDefense(6);
                playerCharacter.setMaxMana(6);
                playerCharacter.setMana(6);
                break;
            case Mage:
                playerCharacter.setIntelligence(8);
                playerCharacter.setDexterity(6);
                playerCharacter.setStrength(4);

                playerCharacter.setMaxHp(8);
                playerCharacter.setHp(8);
                playerCharacter.setDefense(6);
                playerCharacter.setMaxMana(10);
                playerCharacter.setMana(10);
                break;
        }

        user.setPlayerCharacter(playerCharacter);

        loginCallback.onCreatedCharacterWithClass(user);
    }



}
