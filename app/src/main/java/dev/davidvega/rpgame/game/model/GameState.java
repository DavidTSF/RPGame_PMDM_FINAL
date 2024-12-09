package dev.davidvega.rpgame.game.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dev.davidvega.rpgame.data.model.User;
import dev.davidvega.rpgame.game.encounter.Encounter;

public class GameState {
    MutableLiveData<User> user = new MutableLiveData<>();
    MutableLiveData<Encounter> encounter = new MutableLiveData<>();

    public GameState() {
        user.postValue(new User());
        encounter.postValue(new Encounter("Dale al botón y comienza!", Encounter.typeEncounter.START));
    }

    public GameState(User user) {
        this.user.postValue(user);
        encounter.postValue(new Encounter("Dale al botón y comienza!", Encounter.typeEncounter.START));
    }

    public User getUser() {
        return user.getValue();
    }

    public MutableLiveData<User> getUserLiveData() {
        return user;
    }

    public void setUser(User user) {
        this.user.postValue(user);
    }

    public Encounter getEncounter() {
        return encounter.getValue();
    }

    public void setEncounter(Encounter encounter) {
        this.encounter.postValue(encounter);
    }

    public MutableLiveData<Encounter>  getEncounterLiveData() {
        return encounter;
    }

}
