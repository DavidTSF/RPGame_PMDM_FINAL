package dev.davidvega.rpgame.game.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dev.davidvega.rpgame.data.model.User;
import dev.davidvega.rpgame.game.encounter.Encounter;

/**
 * The type Game state.
 */
public class GameState {
    /**
     * The User.
     */
    MutableLiveData<User> user = new MutableLiveData<>();
    /**
     * The Encounter.
     */
    MutableLiveData<Encounter> encounter = new MutableLiveData<>();

    /**
     * Instantiates a new Game state.
     */
    public GameState() {
        user.postValue(new User());
        encounter.postValue(new Encounter("Dale al botón y comienza!", Encounter.typeEncounter.START));
    }

    /**
     * Instantiates a new Game state.
     *
     * @param user the user
     */
    public GameState(User user) {
        this.user.postValue(user);
        encounter.postValue(new Encounter("Dale al botón y comienza!", Encounter.typeEncounter.START));
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user.getValue();
    }

    /**
     * Gets user live data.
     *
     * @return the user live data
     */
    public MutableLiveData<User> getUserLiveData() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user.postValue(user);
    }

    /**
     * Gets encounter.
     *
     * @return the encounter
     */
    public Encounter getEncounter() {
        return encounter.getValue();
    }

    /**
     * Sets encounter.
     *
     * @param encounter the encounter
     */
    public void setEncounter(Encounter encounter) {
        this.encounter.postValue(encounter);
    }

    /**
     * Gets encounter live data.
     *
     * @return the encounter live data
     */
    public MutableLiveData<Encounter>  getEncounterLiveData() {
        return encounter;
    }

}
