package dev.davidvega.rpgame.data.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

import dev.davidvega.rpgame.game.model.PlayerCharacter;


/**
 * The type User.
 */
public class User implements Serializable {
    /**
     * The Username.
     */
    String username;
    /**
     * The Player character.
     */
    MutableLiveData<PlayerCharacter> playerCharacter = new MutableLiveData<>( new PlayerCharacter() );

    /**
     * Instantiates a new User.
     *
     * @param username the username
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param username        the username
     * @param playerCharacter the player character
     */
    public User(String username, PlayerCharacter playerCharacter) {
        this.username = username;
        this.playerCharacter.postValue(playerCharacter);
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets player character.
     *
     * @return the player character
     */
    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter.getValue();
    }

    /**
     * Gets playerdata live data.
     *
     * @return the playerdata live data
     */
    public MutableLiveData<PlayerCharacter> getPlayerdataLiveData() {
        return playerCharacter;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets player character.
     *
     * @param playerCharacter the player character
     */
    public void setPlayerCharacter(PlayerCharacter playerCharacter) {
        this.playerCharacter.postValue(playerCharacter);
    }

    /**
     * Sets playerdata live data.
     *
     * @param playerdata the playerdata
     */
    public void setPlayerdataLiveData(MutableLiveData<PlayerCharacter> playerdata) {
        this.playerCharacter = playerdata;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User {")
                .append("\n  Username: ").append(username != null ? username : "No username")
                .append("\n  PlayerCharacter: ")
                .append(playerCharacter.getValue() != null ? playerCharacter.getValue().toString() : "No character assigned")
                .append("\n}");
        return sb.toString();
    }


}

