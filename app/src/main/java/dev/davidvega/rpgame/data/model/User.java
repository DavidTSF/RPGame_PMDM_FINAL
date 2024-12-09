package dev.davidvega.rpgame.data.model;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

import dev.davidvega.rpgame.game.model.PlayerCharacter;


public class User implements Serializable {
    String username;
    MutableLiveData<PlayerCharacter> playerCharacter = new MutableLiveData<>( new PlayerCharacter() );

    public User(String username) {
        this.username = username;
    }

    public User() {
    }

    public User(String username, PlayerCharacter playerCharacter) {
        this.username = username;
        this.playerCharacter.postValue(playerCharacter);
    }

    public String getUsername() {
        return username;
    }

    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter.getValue();
    }

    public MutableLiveData<PlayerCharacter> getPlayerdataLiveData() {
        return playerCharacter;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter) {
        this.playerCharacter.postValue(playerCharacter);
    }

    public void setPlayerdataLiveData(MutableLiveData<PlayerCharacter> playerdata) {
        this.playerCharacter = playerdata;
    }
}
