package dev.davidvega.rpgame.net.api;


import dev.davidvega.rpgame.data.model.AllItemList;
import dev.davidvega.rpgame.data.model.WeaponList;
import dev.davidvega.rpgame.game.encounter.Encounter;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * The interface Rpg api service.
 */
public interface RPGApiService {
    /**
     * The constant BASE_URL.
     */
    String BASE_URL = "https://api.taxistahosting.com/";

    /**
     * Gets all items.
     *
     * @return the all items
     */
    @GET("allitems")
    Call<AllItemList> getAllItems();

    /**
     * Gets all weapons.
     *
     * @return the all weapons
     */
    @GET("weapons")
    Call<WeaponList> getAllWeapons();

    /**
     * Gets random encounter.
     *
     * @return the random encounter
     */
    @GET("encounter")
    Call<Encounter> getRandomEncounter();


}
