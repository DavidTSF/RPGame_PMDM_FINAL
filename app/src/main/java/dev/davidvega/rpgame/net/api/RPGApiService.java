package dev.davidvega.rpgame.net.api;


import dev.davidvega.rpgame.data.model.WeaponList;
import dev.davidvega.rpgame.game.encounter.Encounter;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RPGApiService {
    String BASE_URL = "https://api.taxistahosting.com/";

    @GET("weapons")
    Call<WeaponList> getAllWeapons();

    @GET("encounter")
    Call<Encounter> getRandomEncounter();


}
