package dev.davidvega.rpgame.data.viewmodel;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import dev.davidvega.rpgame.data.model.WeaponList;
import dev.davidvega.rpgame.game.encounter.Encounter;
import dev.davidvega.rpgame.game.model.Item;
import dev.davidvega.rpgame.net.api.RPGApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class GameModel {

    public interface EncounterCallback {
        void onFinishRetrievingEncounter(Encounter encounter);
    }

    public interface ItemCallback {
        void onFinishRetrievingItems(List<Item> itemList);
    }

    public void getAllItems(RPGApiService service, ItemCallback callback ) {
        Call<WeaponList> weaponCall = service.getAllWeapons();
        weaponCall.enqueue(new Callback<WeaponList>() {
            @Override
            public void onResponse(Call<WeaponList> call, Response<WeaponList> response) {
                response.body().getResults().forEach(
                        weapon -> {
                            Log.d("DEBUG name", weapon.getName());
                            Log.d("DEBUG description", weapon.getDescription());
                            Log.d("DEBUG base_damege", String.valueOf(weapon.getBase_damage()));
                        }
                );
                List<Item> tmpList = new ArrayList<>();

                tmpList.addAll(response.body().getResults());
                callback.onFinishRetrievingItems(tmpList);
            }
            @Override
            public void onFailure(Call<WeaponList> call, Throwable t) {
                Log.d("Error", "NO SE HA PODIDO AGARRAR LA API?" + t.toString());
            }
        });
    }

    public void getEncounter(RPGApiService service, EncounterCallback callback ) {
        Call<Encounter> weaponCall = service.getRandomEncounter();
        weaponCall.enqueue(new Callback<Encounter>() {
            @Override
            public void onResponse(Call<Encounter> call, Response<Encounter> response) {
                callback.onFinishRetrievingEncounter( response.body() );
            }
            @Override
            public void onFailure(Call<Encounter> call, Throwable t) {
                Log.d("Error", "NO SE HA PODIDO AGARRAR LA API?" + t.toString());
            }
        });
    }



}
